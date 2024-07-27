package com.example.BloodXClient.client

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException
import com.example.BloodXClient.client.TokenAccessor

class AccessTokenInterceptor(private val tokenAccessor: TokenAccessor) : ClientHttpRequestInterceptor {

    private val log = LoggerFactory.getLogger(AccessTokenInterceptor::class.java)

    @Throws(IOException::class)
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val accessToken = tokenAccessor.getAccessTokenForCurrentUser()

        accessToken?.let {
            request.headers.add(HttpHeaders.AUTHORIZATION, "${it.tokenType.value} ${it.tokenValue}")
            log.debug("user access token set")
        }

        return execution.execute(request, body)
    }
}
