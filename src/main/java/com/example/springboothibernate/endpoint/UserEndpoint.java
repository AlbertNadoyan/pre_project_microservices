package com.example.springboothibernate.endpoint;

import com.example.springboothibernate.model.User;
import com.example.springboothibernate.security.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserEndpoint {
    @GetMapping
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal CurrentUser currentUser) {
        return ResponseEntity.ok(currentUser.getUser());
    }
}
