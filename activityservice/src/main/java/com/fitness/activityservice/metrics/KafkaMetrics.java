package com.fitness.activityservice.metrics;

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

        public KafkaMetrics(MeterRegistry registry) {

            messagesProduced =
                    Counter.builder("fitness.kafka.produced")
                            .register(registry);

            messagesConsumed =
                    Counter.builder("fitness.kafka.consumed")
                            .register(registry);
        }
}
