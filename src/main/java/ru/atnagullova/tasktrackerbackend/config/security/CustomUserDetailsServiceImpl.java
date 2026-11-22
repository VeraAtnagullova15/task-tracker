package ru.atnagullova.tasktrackerbackend.config.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.atnagullova.tasktrackerbackend.entity.User;
import ru.atnagullova.tasktrackerbackend.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public CustomUserDetailsImpl loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' is not found", email)
        ));

        return new CustomUserDetailsImpl(user);
    }
}
