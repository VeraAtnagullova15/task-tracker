package ru.atnagullova.tasktrackerbackend.config.security;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.atnagullova.tasktrackerbackend.entity.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class CustomUserDetailsImpl implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;
    private String email;
    private String password;

    public CustomUserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


}
