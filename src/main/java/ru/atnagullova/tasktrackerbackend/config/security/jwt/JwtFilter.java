package ru.atnagullova.tasktrackerbackend.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsImpl;
import ru.atnagullova.tasktrackerbackend.config.security.CustomUserDetailsServiceImpl;
import ru.atnagullova.tasktrackerbackend.dto.JwtAuthDto;
import ru.atnagullova.tasktrackerbackend.exception.InvalidJwtException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        JwtAuthDto jwtAuthDto = new JwtAuthDto(token);
        if (!jwtUtil.isTokenValid(jwtAuthDto)) {
            AuthenticationEntryPoint authenticationEntryPoint = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
            authenticationEntryPoint.commence(request, response, new InvalidJwtException("Invalid JWT"));
            return;
        }

        String username = jwtUtil.getSubjectFromToken(jwtAuthDto);
        setCustomUserDetailsToSecurityContextHolder(username);

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(@NonNull HttpServletRequest request) {

        String token = null;

        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            token =  bearerToken.substring("Bearer ".length());
        }

        return token;
    }

    private void setCustomUserDetailsToSecurityContextHolder(String username) {

        CustomUserDetailsImpl customUserDetails = userDetailsService.loadUserByUsername(username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
