package com.poc.bloodx.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.core.context.SecurityContextHolder
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/login/oauth2/code/zitadel")
class OAuth2RedirectController {

    @GetMapping
    fun handleOAuth2Redirect(request: HttpServletRequest, response: HttpServletResponse) {
        // Extract the token from the request or security context
        val token = extractToken(request)
        
        // Redirect to frontend with the token
        response.sendRedirect("http://localhost:3000/callback?token=$token")
    }

    private fun extractToken(request: HttpServletRequest): String {
        val authentication = SecurityContextHolder.getContext().authentication as? OAuth2AuthenticationToken
            ?: throw IllegalStateException("No authentication found")
    
        val oAuth2User = authentication.principal
        val accessToken = oAuth2User.attributes["access_token"] as? OAuth2AccessToken
            ?: throw IllegalStateException("No access token found")
    
        println("${accessToken}*****")
        return accessToken.tokenValue
    }
}
