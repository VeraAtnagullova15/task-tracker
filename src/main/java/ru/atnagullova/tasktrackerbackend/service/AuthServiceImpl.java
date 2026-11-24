package ru.atnagullova.tasktrackerbackend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsImpl;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsServiceImpl;
import ru.atnagullova.tasktrackerbackend.config.security.jwt.JwtUtil;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserCredentialsDto;
import ru.atnagullova.tasktrackerbackend.dto.UserResponseDto;
import ru.atnagullova.tasktrackerbackend.dto.UserSignUpDto;
import ru.atnagullova.tasktrackerbackend.entity.User;
import ru.atnagullova.tasktrackerbackend.exception.BadDataRequestException;
import ru.atnagullova.tasktrackerbackend.exception.UsernameAlreadyExistsException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthDto signUp(UserSignUpDto signUpDto) {

        if (!signUpDto.password().equals(signUpDto.confirmPassword())) {
            throw new BadDataRequestException("Passwords are not equal");
        }
        if (userService.findByEmail(signUpDto.email()).isPresent()) {
            throw new UsernameAlreadyExistsException("This email is already taken");
        }

        User user = new User(signUpDto.email(), passwordEncoder.encode(signUpDto.password()));
        userService.save(user);

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

    @Override
    public JwtAuthDto signIn(UserCredentialsDto userCredentialsDto) {

        if (userService.findByEmail(userCredentialsDto.email()).isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userService.findByEmail(userCredentialsDto.email()).get();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userCredentialsDto.email(), userCredentialsDto.password()));
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Wrong credentials", badCredentialsException);
            throw new BadDataRequestException("Wrong email or password");
        }

        CustomUserDetailsImpl customUserDetails = userDetailsService.loadUserByUsername(userCredentialsDto.email());
        jwtUtil.getJwt(customUserDetails);

        return jwtUtil.getJwt(customUserDetails);
    }

    @Override
    public UserResponseDto getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetailsImpl userDetails = (CustomUserDetailsImpl) authentication.getPrincipal();



        return new UserResponseDto(userDetails.getId(), userDetails.getUsername());

    }
}
