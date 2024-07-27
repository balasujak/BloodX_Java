package com.example.BloodXClient.config.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

import com.example.BloodXClient.client.ZitadelGrantedAuthoritiesMapper

@Configuration
class WebSecurityConfigClient(private val clientRegistrationRepository: ClientRegistrationRepository) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/webjars/**", "/resources/**", "/css/**").permitAll()
                    .anyRequest().fullyAuthenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login
                    .userInfoEndpoint { userInfoEndpointConfig ->
                        userInfoEndpointConfig.userAuthoritiesMapper(userAuthoritiesMapper())
                    }
            }
            .logout { logout ->
                logout.logoutSuccessHandler(oidcLogoutSuccessHandler())
            }

        return http.build()
    }

    private fun userAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return ZitadelGrantedAuthoritiesMapper()
    }

    private fun oidcLogoutSuccessHandler(): LogoutSuccessHandler {
        val oidcLogoutSuccessHandler = OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}")
        return oidcLogoutSuccessHandler
    }
}
