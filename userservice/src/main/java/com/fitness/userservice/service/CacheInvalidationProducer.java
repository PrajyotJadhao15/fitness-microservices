package com.fitness.userservice.service;

import com.fitness.userservice.DTO.UserCacheInvalidationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheInvalidationProducer {


        private final KafkaTemplate<String, UserCacheInvalidationEvent> kafkaTemplate;

        private static final String TOPIC = "user-cache-invalidation";

        public void publishUserCacheInvalidation(UserCacheInvalidationEvent userCacheInvalidationEvent) {
            kafkaTemplate.send(TOPIC, userCacheInvalidationEvent.getCacheName(), userCacheInvalidationEvent);


            log.info("Published User Cache Invalidation For:{} {} ", userCacheInvalidationEvent.getKey(), userCacheInvalidationEvent.getCacheName());
        }
    }
