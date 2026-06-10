package com.example.aiservice.service;

import com.example.aiservice.DTO.AIRecommendationResponseDTO;
import com.example.aiservice.Exceptions.AiResponseParsingException;
import com.example.aiservice.Metrics.AiMetrics;
import com.example.aiservice.Repository.RecommendationRepository;
import com.example.aiservice.model.Activity;
import com.example.aiservice.model.Recommendation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAIResponseService {

    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationMapperService recommendationMapperService;
    private final AiMetrics aiMetrics;

  //  private static final double COST_PER_TOKEN = 0.000001;



    public Recommendation generateRecommendation(Activity activity) {

        String prompt = createPromptForAiService(activity);


      String response=geminiService.getRecommendations(prompt);

      log.info("AI Response: {}", response);

        try {

            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode usageMetadata = rootNode.path("usageMetadata");

//            double totalTokens =
//                    usageMetadata.path("totalTokenCount").asDouble();
//
//       double alltokens=3274;
//
//            aiMetrics.getTotalTokensUsed()
//                    .increment(totalTokens);

//            log.info("Total Tokens Used: {}", totalTokens);
//
//            double estimatedCost = alltokens * COST_PER_TOKEN;
//
//            aiMetrics.getAiCost()
//                    .increment(estimatedCost);

         //   log.info("Estimated AI Cost: {}", estimatedCost);

        } catch (Exception e) {

            aiMetrics.getAiFailures().increment();

            log.error("Failed to extract token usage", e);
        }

       return processAiResponse(activity, response);

    }


    private Recommendation processAiResponse(Activity activity, String response) {


        try{
           // ObjectMapper objectMapper =new ObjectMapper();
            JsonNode rootNode =objectMapper.readTree(response);

              JsonNode textNode=rootNode.path("candidates")
                      .get(0).path("content")
                      .path("parts")
                      .get(0)
                      .path("text");

              String cleanJson= textNode.asText()
                      .replaceAll("(?s)```json\\s*", "")
                      .replaceAll("```", "")
                      .trim();



            int start = cleanJson.indexOf("{");
            int end = cleanJson.lastIndexOf("}");
            String cleanContent= cleanJson.substring(start, end + 1);

            log.info("Response From Clean AI: {}", cleanContent);

              AIRecommendationResponseDTO AIrecommendationDTO=objectMapper
                      .readValue(cleanContent, AIRecommendationResponseDTO.class);


           Recommendation recommendation=recommendationMapperService
                   .recommendationMapping(AIrecommendationDTO, activity);

           recommendationRepository.save(recommendation);

            aiMetrics.getRecommendationsGenerated().increment();

           log.info("Recommendation Response Saved: {}", recommendation);

            return recommendation;


        } catch(Exception e){

            log.error("AI Response not Saved: {}",  response);

            throw new AiResponseParsingException(
                    "Failed to parse AI response",
                    e
            );


        }





    }

    public String createPromptForAiService(Activity activity){

        return String.format("""
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ]
        }

        Analyze this activity:
        Activity Type: %s
        Duration: %d minutes
        Calories Burned: %d
        Additional Metrics: %s
        
        Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
        Ensure the response follows the EXACT JSON format shown above.
        """,

                activity.getType(),
                activity.getDuration(),
                activity.getCalorieBurned(),
                activity.getMetrics()
        );
    }

}
