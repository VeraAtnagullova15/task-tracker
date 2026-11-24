package ru.atnagullova.tasktrackerbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserCredentialsDto;
import ru.atnagullova.tasktrackerbackend.dto.UserResponseDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;

public interface AuthController {

    @PostMapping("/user")
    ResponseEntity<JwtAuthDto> signUp(@RequestBody UserSignUpDto signUpDto);


    @PostMapping("/auth/login")
    ResponseEntity<JwtAuthDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto);

    @GetMapping("/user")
    ResponseEntity<UserResponseDto> getCurrentUser();

}
