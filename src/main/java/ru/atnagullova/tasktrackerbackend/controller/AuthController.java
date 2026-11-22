package ru.atnagullova.tasktrackerbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;

public interface AuthController {

    @PostMapping("/user")
    ResponseEntity<JwtAuthDto> signUp(@RequestBody UserSignUpDto signUpDto);
}
