package ru.atnagullova.tasktrackerbackend.service;

import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;

public interface AuthService {

    JwtAuthDto signUp(UserSignUpDto signUpDto);


}
