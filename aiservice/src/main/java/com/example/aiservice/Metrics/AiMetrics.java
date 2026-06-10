package com.example.aiservice.Metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter

public class AiMetrics {



    private final Counter aiCalls;
    private final Counter aiFailures;
    private final Counter recommendationsGenerated;
    private final Timer aiResponseTimer;
    private final Counter totalTokensUsed;
    private final Counter aiCost;

    public AiMetrics(MeterRegistry registry) {

        this.aiCalls = Counter.builder("fitness.ai.calls")
                .description("Total AI Calls")
                .register(registry);

        this.aiFailures = Counter.builder("fitness.ai.failures")
                .description("Total AI Failures")
                .register(registry);

        this.recommendationsGenerated =
                Counter.builder("fitness.recommendations.generated")
                        .register(registry);

        this.aiResponseTimer =
                Timer.builder("fitness.ai.response.time")
                        .register(registry);

        this.totalTokensUsed = Counter.builder("ai.tokens.used")
                .description("Total AI tokens consumed")
                .register(registry);

       this.aiCost= Counter.builder("ai.cost.usd")
                .register(registry);
    }



}

