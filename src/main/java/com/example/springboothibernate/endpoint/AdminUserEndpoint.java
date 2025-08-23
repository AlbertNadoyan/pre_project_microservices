package com.example.springboothibernate.endpoint;

import com.example.springboothibernate.dto.UserRequest;
import com.example.springboothibernate.exception.UserNotFoundException;
import com.example.springboothibernate.model.User;
import com.example.springboothibernate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserEndpoint {
    private final UserService userService;

    public AdminUserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody UserRequest user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
