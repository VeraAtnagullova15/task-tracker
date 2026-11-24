package ru.atnagullova.tasktrackerbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserCredentialsDto;
import ru.atnagullova.tasktrackerbackend.dto.UserResponseDto;
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

    @Override
    public ResponseEntity<JwtAuthDto> signIn(UserCredentialsDto userCredentialsDto) {
        return ResponseEntity.ok(authService.signIn(userCredentialsDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @GetMapping("/secured")
    public String secured() {
        return "Secured";
    }
}
