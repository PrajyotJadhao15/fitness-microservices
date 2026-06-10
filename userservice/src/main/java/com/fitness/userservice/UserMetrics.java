package com.fitness.userservice;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserMetrics {




        private final Counter cacheHits;
        private final Counter cacheMisses;

        public UserMetrics(MeterRegistry registry) {

            this.cacheHits = Counter.builder("fitness_cache_hits_total")
                    .description("Total cache hits")
                    .register(registry);

            this.cacheMisses = Counter.builder("fitness_cache_misses_total")
                    .description("Total cache misses")
                    .register(registry);
        }
    }
