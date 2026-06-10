package com.fitness.userservice.controller;

import com.fitness.userservice.DTO.AuthResponse;
import com.fitness.userservice.DTO.LoginRequest;
import com.fitness.userservice.DTO.UserDTO;
import com.fitness.userservice.DTO.UserRequest;
import com.fitness.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequest request){

        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

          return new ResponseEntity<AuthResponse>(userService.login(request), HttpStatus.OK);

    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<UserDTO> fetchUserById(@PathVariable Integer id){

       return ResponseEntity.ok(userService.fetchUserById(id));
    }

    @GetMapping("/Validate/{id}")
    public ResponseEntity<Boolean> validate(@PathVariable Integer id){

        return ResponseEntity.ok(userService.ExistsBYId(id));
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id){

       return new ResponseEntity<>(userService.updateUser(userDTO, id), HttpStatus.ACCEPTED);
    }

}
