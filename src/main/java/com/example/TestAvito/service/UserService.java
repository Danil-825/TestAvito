package com.example.TestAvito.service;

import com.example.TestAvito.entity.User;
import com.example.TestAvito.entity.enums.UserRole;
import com.example.TestAvito.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if(userRepository.findByEmail(email) != null) return false;

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Явно инициализируем Set и добавляем роль
        Set<UserRole> role = new HashSet<>();
        role.add(UserRole.ROLE_USER);
        user.setRole(role);

        log.info("Saving new User with email: {}", email);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error saving user: {}", e.getMessage());
            return false;
        }
    }
}
