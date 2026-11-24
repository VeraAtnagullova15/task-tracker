package ru.atnagullova.tasktrackerbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atnagullova.tasktrackerbackend.entity.User;
import ru.atnagullova.tasktrackerbackend.repository.UserRepository;

import java.util.Optional;


public interface UserService {

    Optional<User> findByEmail(String email);

    void save(User user);

}
