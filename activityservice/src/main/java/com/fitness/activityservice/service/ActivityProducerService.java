package com.fitness.activityservice.service;

import com.fitness.activityservice.metrics.KafkaMetrics;
import com.fitness.activityservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityProducerService {

    private final KafkaTemplate<String, Activity> kafkaTemplate;
    private final KafkaMetrics kafkaMetrics;

    @Value("${kafka.topic.name}")
    private String topicName;

      public void sendActivity(Activity activity){

          try{


              kafkaTemplate.send(topicName, activity.getUserId(), activity);

              kafkaMetrics.getMessagesProduced().increment();
              log.info("Send Activity With Id: {}", activity.getUserId());
          } catch(RuntimeException e){

              log.error("Failed To Publish Events {} {} ", activity.getUserId(), e.getMessage());
          }

      }


}


