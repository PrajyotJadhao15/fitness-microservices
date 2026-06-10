package com.fitness.activityservice.service;

import com.fitness.activityservice.DTO.ActivityDTO;
import com.fitness.activityservice.DTO.ActivityRequest;
import com.fitness.activityservice.Exceptions.ActivityNotFoundException;
import com.fitness.activityservice.Exceptions.InvalidUserException;
import com.fitness.activityservice.Exceptions.KafkaPublishException;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;
    private final UserValidateService userValidateService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;
    private final ActivityProducerService activityProducerService;
    private final Cache<String, ActivityDTO> userCache;
    private final RedisTemplate<String, ActivityDTO> redisTemplate;

    public ActivityService(ActivityRepository activityRepository, ModelMapper modelMapper, UserValidateService userValidateService, KafkaTemplate<String, Activity> kafkaTemplate, ActivityProducerService activityProducerService, Cache<String, ActivityDTO> userCache, RedisTemplate<String, ActivityDTO> redisTemplate) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
        this.userValidateService = userValidateService;
        this.kafkaTemplate = kafkaTemplate;
        this.activityProducerService = activityProducerService;
        this.userCache = userCache;
        this.redisTemplate = redisTemplate;
    }


    public ActivityDTO trackActivity(ActivityRequest request){

        Boolean isValidUser= userValidateService.validateUser(Integer.valueOf(request.getUserId()));
        if(!isValidUser){

            log.error("Invalid User With Id: "+request.getUserId());
            throw new InvalidUserException(
                    "Invalid User Id: " + request.getUserId()
            );
        }

        Activity activity= activityRepository.save(modelMapper.map(request, Activity.class));



        try{

           activityProducerService.sendActivity(activity);

        } catch(Exception e){

            log.error("Failed to publish activity event", e);

            throw new KafkaPublishException("Failed to publish activity event", e);
        }


        return  modelMapper.map(activity, ActivityDTO.class ) ;

    }


    public ActivityDTO getActivity(String id){

        String key="activity:"+id;

        ActivityDTO cache=userCache.getIfPresent(key);

        if (cache != null) {

            log.info("L1 Cache Hit: (Caffeine) {}", key);
            return cache;

        }

        log.info("L1 Cache Miss: {}", key);

        ActivityDTO redisCache=redisTemplate.opsForValue().get(key);

        if(redisCache!=null){

            log.info("L2 Cache Hit: (Redis) {}", key);

            userCache.put(key, redisCache);

            return redisCache;



        }

        log.info("L2 Cache Miss: {}", key);



        Activity activity =activityRepository.findById(id).orElseThrow(
                ()-> new ActivityNotFoundException("Activity not found with id: " + id));

        log.info("Fetch From DB: {}", id);

       ActivityDTO activityDTO= modelMapper.map(activity, ActivityDTO.class);

        userCache.put(key, activityDTO);

        // Store in L2 Cache
        redisTemplate.opsForValue().set(key, activityDTO);

        log.info("Data Populated In L1 And L2 Cache After Fetching From Db");

        return activityDTO;
    }
}
