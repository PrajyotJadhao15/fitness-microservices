package com.fitness.userservice.service;

import com.fitness.userservice.DTO.UserCacheInvalidationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CacheInvalidationConsumer {



    private final CacheManager cacheManager;

    public CacheInvalidationConsumer(CacheManager cacheManager) {
        this.cacheManager = cacheManager;


    }

        @KafkaListener(
                topics = "user-cache-invalidation",
                groupId = "${spring.application.name}-${random.uuid}"
        )
        public void handle(UserCacheInvalidationEvent event) {

            String key=event.getCacheName()+":"+event.getKey();

            Cache cache=cacheManager.getCache(event.getCacheName());

            if(cache!=null){

                 cache.evict(key);

                 log.info("Caffeine Cache Invalidation: {}", key);
            }
        }
    }

