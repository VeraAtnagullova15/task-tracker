package ru.atnagullova.tasktrackerbackend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.dto.UserDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private long jwtLifetime;

    public JwtAuthDto getJwt(UserDto userDto) {

        return new JwtAuthDto(generateToken(userDto));
    }

    private String generateToken(UserDto userDto) {

        Date date = Date.from(LocalDateTime.now().plusMinutes(jwtLifetime).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(userDto.username())
                .expiration(date)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

}
