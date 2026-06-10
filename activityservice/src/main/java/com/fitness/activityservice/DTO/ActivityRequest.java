package com.fitness.activityservice.DTO;

import com.fitness.activityservice.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequest {


    private String userId;  // Associated with User...
    private ActivityType type;
    private Integer duration;
    private Integer calorieBurned;
    private LocalDateTime startTime;
    private Map<String, Object> metrics;

}
