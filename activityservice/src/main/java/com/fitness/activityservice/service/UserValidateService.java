package com.fitness.activityservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidateService {

    private final WebClient webClient;


    public Boolean validateUser(Integer userId){

           return webClient
                   .get()
                   .uri("/api/users/Validate/{id}", userId)
                   .retrieve()//sends request and waits for the response and extracts it..
                   .bodyToMono(Boolean.class)//  converts http response into boolean
                   .block();
                   // for synchronous calls
           //if we dont use .block then asynchronous calls will happen..
       }
}
