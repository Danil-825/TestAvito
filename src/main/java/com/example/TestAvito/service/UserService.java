package com.example.TestAvito.service;

import com.example.TestAvito.entity.User;
import com.example.TestAvito.entity.enums.UserRole;
import com.example.TestAvito.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.ROLE_USER);
        roles.add(UserRole.ROLE_ADMIN);
        user.setRole(roles);

        log.info("Saving new User with email: {}", email);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            return false;
        }
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Banning user with id: {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unbanning user with id: {}; email: {}", user.getId(), user.getEmail());
            }
            userRepository.save(user);
        }
    }

    public void changeUserRoles(User user, Map <String, String> form) {
        if (user == null || form == null) {
            throw new IllegalArgumentException("User and form cannot be null");
        }

        Set<String> availableRoles = Arrays.stream(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toSet());

        // Инициализируем роли, если их нет
        Set<UserRole> userRoles = user.getRole();
        if (userRoles == null) {
            userRoles = new HashSet<>();
            user.setRole(userRoles);
        } else {
            userRoles.clear();
        }

        // Добавляем новые роли из формы
        for (String roleName : form.keySet()) {
            if (availableRoles.contains(roleName)) {
                userRoles.add(UserRole.valueOf(roleName));
            }
        }

        userRepository.save(user);
    }
}
