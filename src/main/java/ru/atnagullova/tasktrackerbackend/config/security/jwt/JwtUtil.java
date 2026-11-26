package ru.atnagullova.tasktrackerbackend.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsImpl;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsServiceImpl;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final CustomUserDetailsServiceImpl customUserDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private long jwtLifetime;

    public JwtAuthDto getJwt(CustomUserDetailsImpl customUserDetails) {

        return new JwtAuthDto(generateToken(customUserDetails));
    }

    public String getSubjectFromToken(JwtAuthDto jwtAuthDto) {

        String token = jwtAuthDto.token();

        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean isTokenValid(JwtAuthDto jwtAuthDto) {

        String token = jwtAuthDto.token();
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;

        } catch (ExpiredJwtException expiredJwtException) {
            log.debug("Token is expired", expiredJwtException);
        } catch (SignatureException signatureException) {
            log.debug("Signature is wrong", signatureException);
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.debug("Unsupported jwt", unsupportedJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            log.debug("Malformed jwt", malformedJwtException);
        } catch (SecurityException securityException) {
            log.debug("Security exception", securityException);
        } catch (Exception e) {
            log.debug("Unexpected error while checking token", e);
        }

        return false;
    }

    private String generateToken(CustomUserDetailsImpl customUserDetails) {

        Date date = Date.from(LocalDateTime.now().plusMinutes(jwtLifetime).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .expiration(date)
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
