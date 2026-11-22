package ru.atnagullova.tasktrackerbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsImpl;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsServiceImpl;
import ru.atnagullova.tasktrackerbackend.config.security.jwt.JwtUtil;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;
import ru.atnagullova.tasktrackerbackend.entity.User;
import ru.atnagullova.tasktrackerbackend.exception.BadDataRequestException;
import ru.atnagullova.tasktrackerbackend.exception.UsernameAlreadyExists;
import ru.atnagullova.tasktrackerbackend.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthDto signUp(UserSignUpDto signUpDto) {

        if (!signUpDto.password().equals(signUpDto.confirmPassword())) {
            throw new BadDataRequestException("Passwords are not equal");
        }
        if (userRepository.findByEmail(signUpDto.email()).isPresent()) {
            throw new UsernameAlreadyExists("This email is already taken");
        }

        User user = new User(signUpDto.email(), passwordEncoder.encode(signUpDto.password()));
        userRepository.save(user);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signUpDto.email(), signUpDto.password()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Wrong credentials", badCredentialsException);
            throw new BadDataRequestException("Wrong email or password");
        }
        CustomUserDetailsImpl customUserDetails = userDetailsService.loadUserByUsername(signUpDto.email());

        return jwtUtil.getJwt(customUserDetails);

    }
}
