package ru.atnagullova.tasktrackerbackend.dto;

public record UserSignUpDto(String email, String password, String confirmPassword) {
}
