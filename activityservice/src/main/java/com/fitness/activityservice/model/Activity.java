package com.fitness.activityservice.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "ActivityFitness")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {


     @Id
     private String id;

     private String userId;  // Associated with User...

     private ActivityType type;

     private Integer duration;

     private Integer calorieBurned;

     private LocalDateTime startTime;


     private Map<String, Object> metrics;

     @CreatedDate
     private LocalDateTime createdAt;

     @LastModifiedDate
     private LocalDateTime updatedAt;

}
