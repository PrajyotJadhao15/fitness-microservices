package com.fitness.activityservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ModelMapperConfig {

    @Bean
      public ModelMapper modelMapper(){

          return new ModelMapper();
      }





        @Bean
        public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
            return builder.build(); // Spring auto registers JavaTimeModule

        }
    }


