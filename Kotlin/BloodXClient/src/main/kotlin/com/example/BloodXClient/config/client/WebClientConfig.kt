package com.example.BloodXClient.config.client

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import com.example.BloodXClient.client.TokenAccessor
import com.example.BloodXClient.client.AccessTokenInterceptor


@Configuration
class WebClientConfig(private val tokenAccessor: TokenAccessor) {

    @Bean
    @Qualifier("zitadel")
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .interceptors(AccessTokenInterceptor(tokenAccessor))
            .build()
    }
}
