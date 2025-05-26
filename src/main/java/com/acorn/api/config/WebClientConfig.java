package com.acorn.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${naver.map.base-url}")
    private String naverBaseUrl;

    @Value("${naver.map.client-id}")
    private String naverClientId;

    @Value("${naver.map.client-secret}")
    private String naverClientSecret;

    @Value("${kakaopay.api.base-url}")
    private String kakaoPayBaseUrl;

    @Value("${kakaopay.api.secret-key}")
    private String kakaoPaySecretKey;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(naverBaseUrl)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", naverClientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", naverClientSecret)
                .defaultHeader("Accept", "application/json")
                .build();
    }

    @Bean
    public WebClient kakaoPayWebClient() {
        return WebClient.builder()
                .baseUrl(kakaoPayBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "SECRET_KEY " + kakaoPaySecretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}