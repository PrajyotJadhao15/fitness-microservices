package com.example.aiservice.service;


import com.example.aiservice.Exceptions.GeminiApiException;
import com.example.aiservice.Metrics.AiMetrics;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final WebClient webClient;
    private final AiMetrics aiMetrics;
    private final MeterRegistry meterRegistry;


    @Value("${gemini.api.url}")
    private String Gemini_Url;

    @Value("${gemini.api.key}")
    private String Gemini_Api_Key;



    @CircuitBreaker(
            name = "geminiApi",
            fallbackMethod = "fallbackRecommendation"
    )
        public String getRecommendations(String prompt){

            Map<String, Object> requestBody= Map.of(
                    "contents", new Object[]{
                            Map.of("parts", new Object[] {

                                    Map.of("text", prompt)
                            })
                    }
            );

        aiMetrics.getAiCalls().increment();

        Timer.Sample sample = Timer.start(meterRegistry);

            try {


                String response = webClient.post()
                        .uri(Gemini_Url)
                        .header("Content-Type", "application/json")
                        .header("x-goog-api-key", Gemini_Api_Key)
                        .bodyValue(requestBody)
                        .retrieve()
                        .onStatus(
                                HttpStatusCode::isError,
                                clientResponse -> clientResponse.bodyToMono(String.class)
                                        .flatMap(errorBody -> {
                                            log.error("Gemini Error Response: {}", errorBody);
                                            return reactor.core.publisher.Mono.error(
                                                    new RuntimeException(errorBody));
                                        })
                        )
                        .bodyToMono(String.class)
                        .block();


                return response;

            } catch (WebClientResponseException e) {

                log.error("Error while calling Gemini API", e);

                aiMetrics.getAiFailures().increment();
                throw new GeminiApiException(" Gemini returned HTTP " + e.getStatusCode(), e);
            }
            catch (Exception ex){

                aiMetrics.getAiFailures().increment();

                log.error("Unexpected error while calling Gemini API", ex);

                throw new GeminiApiException(
                        "Gemini API unavailable",
                        ex
                );

            } finally {

                sample.stop(aiMetrics.getAiResponseTimer());
            }
        }

    public String fallbackRecommendation(String prompt, Exception ex) {

        log.error("Gemini API unavailable: {}", ex.getMessage());

        return """
                {
                  "message":"AI recommendation currently unavailable"
                }
                """;



    }



}
