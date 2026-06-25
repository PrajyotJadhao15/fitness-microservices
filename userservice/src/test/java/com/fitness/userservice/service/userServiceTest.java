package com.fitness.userservice.service;

import com.fitness.userservice.DTO.UserDTO;
import com.fitness.userservice.DTO.UserRequest;
import com.fitness.userservice.model.User;
import com.fitness.userservice.model.UserRole;
import com.fitness.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class userServiceTest{

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserCacheService userCacheService;

    @Test
    public void testRegisterUser(){

        UserRequest userRequest=new UserRequest();

        userRequest.setFirstName("Prajyot");
        userRequest.setLastName("Jadhao");
        userRequest.setEmail("prajyotjadhao321@gmail.com");
        userRequest.setPassword("Prajyot@321");
        userRequest.setRole(UserRole.USER);

        User user=new User();
        user.setEmail("prajyotjadhao321@gmail.com");

        UserDTO dto=new UserDTO();
        dto.setEmail("prajyotjadhao321@gmail.com");

        when(userRepository.existsByEmail(userRequest.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(userRequest.getPassword()))
                .thenReturn("Encoded"+userRequest.getPassword());

        when(modelMapper.map(any(User.class), eq(User.class)))
                .thenReturn(user);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        when(modelMapper.map(any(User.class), eq(UserDTO.class)))
                .thenReturn(dto);

        UserDTO userDTO=userService.register(userRequest);

        assertNotNull(userDTO);
        assertEquals(
                userRequest.getEmail(),
                userDTO.getEmail());


        verify(userRepository)
                .existsByEmail(userRequest.getEmail());

        verify(passwordEncoder)
                .encode(userRequest.getPassword());

        verify(userRepository)
                .save(any(User.class));

    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        UserRequest userRequest=new UserRequest();
        userRequest.setEmail("prajyotjadhao321@gmail.com");

        when(userRepository.existsByEmail(userRequest.getEmail()))
                .thenReturn(true);

       RuntimeException ex = assertThrows(RuntimeException.class,
               ()-> userService.register(userRequest));

       assertEquals("Email Already Exist: "+ userRequest.getEmail(), ex.getMessage());

       verify(userRepository).existsByEmail(userRequest.getEmail());
    }

}
