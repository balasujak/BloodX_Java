package com.example.BloodXClient.client

import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.stereotype.Component

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient

@Component
class TokenAccessor(private val authorizedClientService: OAuth2AuthorizedClientService) {

    private val log = LoggerFactory.getLogger(TokenAccessor::class.java)

    fun getAccessTokenForCurrentUser(): OAuth2AccessToken? {
        return getAccessToken(SecurityContextHolder.getContext().authentication)
    }

    fun getAccessToken(auth: Authentication): OAuth2AccessToken? {
        log.debug("Get AccessToken for current user {}: begin", auth.name)
        val authToken = auth as? OAuth2AuthenticationToken
        val clientId = authToken?.authorizedClientRegistrationId ?: return null
        val username = auth.name
        val oauth2Client = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(clientId, username)

        if (oauth2Client == null) {
            log.warn("Get AccessToken for current user failed: client not found")
            return null
        }

        val accessToken = oauth2Client.accessToken
        log.debug("Get AccessToken for current user {}: end", auth.name)
        return accessToken
    }
}
