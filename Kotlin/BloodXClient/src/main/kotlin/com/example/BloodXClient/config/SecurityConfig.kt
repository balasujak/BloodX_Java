package com.example.BloodXClient.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.http.HttpMethod

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .csrf { csrf ->
                csrf.disable() // Disable CSRF if appropriate
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests
                    .anyRequest().authenticated() // Secure all other endpoints
            }
        return http.build()
    }

    // @Bean
    // fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    //     http
    //         .authorizeHttpRequests { authorizeRequests ->
    //             authorizeRequests
    //                 .requestMatchers("/", "/login**", "/error**").permitAll()
    //                 .anyRequest().authenticated()
    //         }
    //         .oauth2Login()
    //         .and()
    //         .cors()
    //         .and()
    //         .csrf()
    //         .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

    //     return http.build()
    // }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000") // Ensure this matches your frontend URL
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
