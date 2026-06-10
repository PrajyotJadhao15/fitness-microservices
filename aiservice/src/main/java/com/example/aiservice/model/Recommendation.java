package com.example.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendations")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Recommendation {

    @Id
    private String id;

    private String activityId;

    private String userId;

    private String recommendation;

    private Analysis analysis;

    private List<Improvements> improvements;

    private  List<Suggestion> suggestions;

    private List<String> safety;

    @CreatedDate
    private LocalDateTime createdAt;


}
