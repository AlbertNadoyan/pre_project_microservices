package com.example.springboothibernate.service;

import com.example.springboothibernate.dto.UserRequest;
import com.example.springboothibernate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUser(UserRequest userRequest);

    List<User> getUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, UserRequest user);

    void deleteUser(Long id);
}
