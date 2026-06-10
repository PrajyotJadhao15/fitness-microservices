package com.example.aiservice.service;

import com.example.aiservice.DTO.RecommendationDTO;
import com.example.aiservice.Metrics.AiMetrics;
import com.example.aiservice.Metrics.CacheMetrics;
import com.example.aiservice.Repository.RecommendationRepository;
import com.example.aiservice.model.Recommendation;
import com.github.benmanes.caffeine.cache.Cache;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecommendationService {

    private static final double COST_PER_TOKEN = 0.000003;
    private final RecommendationRepository recommendationRepository;
    private final ModelMapper modelMapper;
    private final Cache<String, RecommendationDTO> cache;
    private final RedisTemplate<String, RecommendationDTO> redisTemplate;
    private final CacheMetrics cacheMetrics;
    private final AiMetrics aiMetrics;

    public RecommendationService(RecommendationRepository recommendationRepository, ModelMapper modelMapper, Cache<String, RecommendationDTO> cache, RedisTemplate<String, RecommendationDTO> redisTemplate, CacheMetrics cacheMetrics, AiMetrics aiMetrics) {
        this.recommendationRepository = recommendationRepository;
        this.modelMapper = modelMapper;
        this.cache = cache;
        this.redisTemplate = redisTemplate;

        this.cacheMetrics = cacheMetrics;
        this.aiMetrics = aiMetrics;
    }

    public List<RecommendationDTO> getUserRecommendation(String userId) {



        double alltokens=3274;

            aiMetrics.getTotalTokensUsed()
                    .increment(alltokens);

            log.info("Total Tokens Used: {}", alltokens);

            double estimatedCost = alltokens * COST_PER_TOKEN;

            aiMetrics.getAiCost()
                    .increment(estimatedCost);

           log.info("Estimated AI Cost: {}", estimatedCost);

        return recommendationRepository.findByUserId(userId)
                .stream()
                .map(recommendation ->modelMapper
                        .map(recommendation, RecommendationDTO.class))
                .toList();

    }

    public RecommendationDTO getActivityRecommendation(String activityId) {

        String key="recommendation:"+activityId;

        RecommendationDTO caffeineCache=cache.getIfPresent(key);

        if (caffeineCache != null) {

            log.info("L1 Cache Hit :{}", key);

            cacheMetrics.getCacheHits().increment();
            return caffeineCache;

        }
         cacheMetrics.getCacheMisses().increment();
        log.info("L1 Cache Miss : {},", key);

        RecommendationDTO redisCache=redisTemplate.opsForValue().get(key);
        if (redisCache != null) {

            log.info("L2 Cache Hit : {}", key);
            cacheMetrics.getCacheHits().increment();
            return redisCache;
        }

        cacheMetrics.getCacheMisses().increment();
        log.info("L2 Cache Miss : {}", key);

        Recommendation recommendation=recommendationRepository
                .findByActivityId(activityId).orElseThrow(
                        ()-> new RuntimeException("Recommendation Not Found In Db: "+ activityId));

        log.info("Fetch From Database: "+activityId);

        return modelMapper
                .map(recommendation, RecommendationDTO.class);

    }
}
