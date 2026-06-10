package com.fitness.userservice.DTO;

import com.fitness.userservice.model.UserRole;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {



    private String firstName;

    private String lastName;

    private String email;

    private String password;

   private UserRole role;
}
