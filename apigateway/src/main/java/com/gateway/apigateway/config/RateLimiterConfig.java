package com.gateway.apigateway.config;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {


//      @Bean
//      public KeyResolver userKeyResolver(){
//
//            return exchange -> Mono.justOrEmpty(
//                    exchange.getRequest()
//                            .getHeaders()
//                            .getFirst("user-id")
//            ).switchIfEmpty(Mono.just("anonymous"));
//
//      }

      @Bean
      public KeyResolver userKeyResolver() {
            return exchange -> {
                  String auth = exchange.getRequest()
                          .getHeaders()
                          .getFirst(HttpHeaders.AUTHORIZATION);

                  if (auth == null) {
                        return Mono.just(exchange.getRequest()
                                .getRemoteAddress()
                                .getAddress()
                                .getHostAddress());
                  }

                  return Mono.just(auth);
            };
      }



}
