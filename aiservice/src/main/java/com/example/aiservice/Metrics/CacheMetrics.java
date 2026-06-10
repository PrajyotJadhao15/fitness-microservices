package com.example.aiservice.Metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CacheMetrics {



        private final Counter cacheHits;
        private final Counter cacheMisses;

        public CacheMetrics(MeterRegistry registry) {

            cacheHits = Counter.builder("fitness.cache.hits")
                    .register(registry);

            cacheMisses = Counter.builder("fitness.cache.misses")
                    .register(registry);
        }

    }


