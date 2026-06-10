package com.fitness.activityservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.activityservice.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {


    private String id;
    private String userId;  // Associated with User...
    private ActivityType type;
    private Integer duration;
    private Integer calorieBurned;
    @JsonIgnore
    private LocalDateTime startTime;
    private Map<String, Object> metrics;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;

}
