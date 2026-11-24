package ru.atnagullova.tasktrackerbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserCredentialsDto;
import ru.atnagullova.tasktrackerbackend.dto.UserResponseDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;

public interface AuthService {

    JwtAuthDto signUp(UserSignUpDto signUpDto);

    JwtAuthDto signIn(UserCredentialsDto userCredentialsDto);

    UserResponseDto getCurrentUser();


}
