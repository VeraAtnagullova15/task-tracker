package ru.atnagullova.tasktrackerbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;
import ru.atnagullova.tasktrackerbackend.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<JwtAuthDto> signUp(UserSignUpDto signUpDto) {

        return ResponseEntity.ok(authService.signUp(signUpDto));
    }
}
