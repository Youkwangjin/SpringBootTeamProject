package com.acorn.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${naver.map.base-url}")
    private String baseUrl;

    @Value("${naver.map.client-id}")
    private String clientId;

    @Value("${naver.map.client-secret}")
    private String clientSecret;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}