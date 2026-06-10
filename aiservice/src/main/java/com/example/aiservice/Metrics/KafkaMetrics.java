package com.example.aiservice.Metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Setter
@Getter
public class KafkaMetrics {


    private final Counter messagesProduced;
    private final Counter messagesConsumed;
    private final Counter messagesFailed;

    public KafkaMetrics(MeterRegistry registry) {

        messagesProduced =
                Counter.builder("fitness.kafka.produced")
                        .register(registry);

        messagesConsumed =
                Counter.builder("fitness.kafka.consumed")
                        .register(registry);
        messagesFailed = Counter.builder("fitness.kafka.messagefailed")
                .register(registry);
    }
}
