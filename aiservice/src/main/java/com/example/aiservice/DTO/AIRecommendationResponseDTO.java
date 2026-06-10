package com.example.aiservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AIRecommendationResponseDTO {


        private AnalysisDTO analysis;
        private List<ImprovementDTO> improvements;
        private List<SuggestionDTO> suggestions;
        private List<String> safety;
    }


