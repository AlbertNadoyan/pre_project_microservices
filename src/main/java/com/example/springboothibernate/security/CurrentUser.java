package com.example.springboothibernate.security;

import com.example.springboothibernate.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
