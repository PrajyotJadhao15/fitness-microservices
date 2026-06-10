package com.fitness.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.userservice.model.UserRole;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private UserRole role= UserRole.USER;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;

}
