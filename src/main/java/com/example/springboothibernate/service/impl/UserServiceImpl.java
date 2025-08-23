package com.example.springboothibernate.service.impl;

import com.example.springboothibernate.dto.UserRequest;
import com.example.springboothibernate.kafka.event.UserActionEvent;
import com.example.springboothibernate.kafka.producer.UserActionProducer;
import com.example.springboothibernate.model.Role;
import com.example.springboothibernate.model.User;
import com.example.springboothibernate.repository.RoleRepository;
import com.example.springboothibernate.repository.UserRepository;
import com.example.springboothibernate.service.DelayedEmailService;
import com.example.springboothibernate.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActionProducer producer;
    private final DelayedEmailService delayedEmailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           UserActionProducer producer, DelayedEmailService delayedEmailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.producer = producer;
        this.delayedEmailService = delayedEmailService;
    }

    @Override
    public User addUser(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setAge(userRequest.getAge());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Set<Role> roles = userRequest.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + roleName)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        producer.sendUserAction(new UserActionEvent(
                user.getEmail(),
                "CREATED_USER",
                LocalDateTime.now()
        ));

        delayedEmailService.sendEmailWithDelay(user.getEmail(),
                "New user was created",
                "Welcome " + user.getEmail() + "! Your account has been successfully created.",
                Duration.ofMinutes(5));

        return savedUser;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, UserRequest user) {
        User userById = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getName() != null && !user.getName().isEmpty()) {
            userById.setName(user.getName());
        }
        if (user.getSurname() != null && !user.getSurname().isEmpty()) {
            userById.setSurname(user.getSurname());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userById.setEmail(user.getEmail());
        }
        if (user.getAge() != null) {
            userById.setAge(user.getAge());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userById.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> updatedRoles = user.getRoles().stream()
                    .map(role -> roleRepository.findByName(role)
                            .orElseThrow(() -> new IllegalArgumentException("Role not found: " + role)))
                    .collect(Collectors.toSet());
            userById.setRoles(updatedRoles);
        }
        return userRepository.save(userById);
    }

    @Override
    public void deleteUser(Long id) {
        User userById = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(userById);
    }
}
