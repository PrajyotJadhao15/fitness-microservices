package com.fitness.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

       @Bean
      public OpenAPI userServiceApi(){

          return new OpenAPI().info(

                  new Info() .title("User Service Api")
                          .version("1.0")
                          .description("APIs for User Management")
          );
      }
}
