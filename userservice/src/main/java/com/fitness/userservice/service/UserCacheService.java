package com.fitness.userservice.service;

import com.fitness.userservice.DTO.UserCacheInvalidationEvent;
import com.fitness.userservice.DTO.UserDTO;
import com.fitness.userservice.UserMetrics;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class UserCacheService {

    private final Cache<String, UserDTO> userCache;
    private final RedisTemplate<String, UserDTO> redisTemplate;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CacheInvalidationProducer cacheInvalidationProducer;
    private final UserMetrics userMetrics;

    public UserCacheService(Cache<String, UserDTO> userCache, RedisTemplate<String, UserDTO> redisTemplate, UserRepository userRepository, ModelMapper modelMapper, CacheInvalidationProducer cacheInvalidationProducer, UserMetrics userMetrics) {
        this.userCache = userCache;
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cacheInvalidationProducer = cacheInvalidationProducer;
        this.userMetrics = userMetrics;
    }

    public UserDTO fetchByUserId(Integer id){


       try{

           log.info("Inside fetchByUserId: {}", id);

           String key = "user:" + id;

           log.info("Checking L1 cache");

           UserDTO caffeineUser=userCache.getIfPresent(key);

           if(caffeineUser!=null){

               log.info("L1 Cache Hit {}", key);
               userMetrics.getCacheHits().increment();
               return caffeineUser;

           }

           log.info("L1 Cache Miss {}", key);
           userMetrics.getCacheMisses().increment();

           UserDTO redisCache= redisTemplate.opsForValue().get(key);

           if(redisCache!=null){

               log.info("L2 Cache Hit {}", key);
               userMetrics.getCacheHits().increment();
               userCache.put(key, redisCache);
               return redisCache;

           }

           log.info("L2 Cache Miss {}", key);

           userMetrics.getCacheMisses().increment();

           User user = userRepository.findById(id)
                   .orElseThrow(()-> new UsernameNotFoundException("User Not Found For Id: "+ id));

           log.info("DB Hit {}", key);

           UserDTO dto = modelMapper.map(user, UserDTO.class);

           redisTemplate.opsForValue().set(key, dto, Duration.ofMinutes(10));

           log.info("Stored in L2 (Redis) {}", key);

           userCache.put(key, dto);

           log.info("Stored in L1 (Caffeine) {}", key);

           return dto;




       } catch(Exception e){

           log.error("Exception while fetching user", e);
           throw e;
       }

//        log.info("Inside fetchByUserId: {}", id);
//
//        String key = "user:" + id;
//
//        log.info("Checking L1 cache");
//
//           UserDTO caffeineUser=userCache.getIfPresent(key);
//
//           if(caffeineUser!=null){
//
//               log.info("L1 Cache Hit {}", key);
//               return caffeineUser;
//
//           }
//
//           log.info("L1 Cache Miss {}", key);
//
//          UserDTO redisCache= redisTemplate.opsForValue().get(key);
//
//          if(redisCache!=null){
//
//              log.info("L2 Cache Hit {}", key);
//              userCache.put(key, redisCache);
//              return redisCache;
//
//        }
//          log.info("L2 Cache Miss {}", key);
//
//
//       User user= userRepository.findById(id).orElseThrow(
//               ()-> new UsernameNotFoundException("User Not Found For Id: "+ id));
//
//         UserDTO dto =modelMapper.map(user, UserDTO.class);
//
//         userCache.put(key, dto);
//
//         redisTemplate.opsForValue().set(key, dto, Duration.ofMinutes(10));
//
//        log.info("User fetched from DB: {}", key);
//
//         return dto;

    }

    public void evict(Integer id) {

        String key = "user:" + id;

        userCache.invalidate(key);

        redisTemplate.delete(key);

        UserCacheInvalidationEvent invalidationEvent= new UserCacheInvalidationEvent(key, "user:");
        cacheInvalidationProducer.publishUserCacheInvalidation(invalidationEvent);

        log.info("Cache evicted: {}", key);
    }

    public void storeInCache(Integer id, UserDTO userDTO){

         String key ="user:"+id;

         userCache.put(key, userDTO);
         log.info("Stored In L1 Cache (Caffeine) : {}", key);


         redisTemplate.opsForValue().set(key, userDTO);
        log.info("Stored In L1 Cache (Redis) : {}", key);

    }
}
