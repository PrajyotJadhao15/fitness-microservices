package com.example.aiservice.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


        @Bean
        public WebClient.Builder webClientBuilder() {
            return WebClient.builder();
        }


    @Bean
    public WebClient GeminiService(WebClient.Builder webClient) {
        return webClient.build();
    }
    }


