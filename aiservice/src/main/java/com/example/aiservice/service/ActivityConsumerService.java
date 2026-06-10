package com.example.aiservice.service;

import com.example.aiservice.Metrics.KafkaMetrics;
import com.example.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityConsumerService {

       private final ActivityAIResponseService activityAIResponseService;
       private final KafkaMetrics kafkaMetrics;


        @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity_processor_group")
        public void consumeActivity(Activity activity) {


            log.info("✅ Received Activity from Kafka: {}", activity);
            log.info("User ID: {}", activity.getUserId());
            log.info("Activity Type: {}", activity.getType());
            log.info("Duration: {}", activity.getDuration());
            kafkaMetrics.getMessagesConsumed().increment();
            log.info("Recieved Activity From Kafka {}", activity.getUserId());


            try {

                activityAIResponseService.generateRecommendation(activity);


            } catch (Exception e) {

                kafkaMetrics.getMessagesFailed().increment();

                log.error("Failed To Consume Events {} : ",activity.getUserId(), e);
            }

        }
    }







