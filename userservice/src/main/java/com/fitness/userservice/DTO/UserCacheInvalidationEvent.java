package com.fitness.userservice.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCacheInvalidationEvent {


    private String cacheName;
    private String key;

    }

