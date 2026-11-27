package ru.atnagullova.tasktrackerbackend.service;

import ru.atnagullova.tasktrackerbackend.entity.User;

import java.util.Optional;


public interface UserService {

    Optional<User> findByEmail(String email);

    void save(User user);

}
