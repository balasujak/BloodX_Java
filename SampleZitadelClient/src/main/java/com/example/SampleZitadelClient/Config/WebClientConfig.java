package com.example.SampleZitadelClient.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.SampleZitadelClient.ClientConfig.AccessTokenInterceptor;
import com.example.SampleZitadelClient.ClientConfig.TokenAccessor;

@Configuration
@RequiredArgsConstructor
class WebClientConfig {

    private final TokenAccessor tokenAccessor;

    @Bean
    @Qualifier("zitadel")
    RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .defaultHeader("content-type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("accept", MediaType.APPLICATION_JSON_VALUE)
                .interceptors(new AccessTokenInterceptor(tokenAccessor))
                .build();
    }
}