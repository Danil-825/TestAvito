package com.example.TestAvito.service;


import com.example.TestAvito.entity.User;
import com.example.TestAvito.entity.enums.UserRole;
import com.example.TestAvito.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.getRoles().add(UserRole.ROLE_USER);
        userRepository.save(user);
        log.info("Saving new User with email: {}", email);
        return true;
    }
}
