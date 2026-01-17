package com.example.musicbackend.service;

import com.example.musicbackend.entity.User;
import com.example.musicbackend.model.Enum.Role;
import com.example.musicbackend.model.Enum.StatusAccount;
import com.example.musicbackend.model.request.LoginRequest;
import com.example.musicbackend.model.request.RegisterRequest;
import com.example.musicbackend.repository.UserRepository;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        Slugify slugify = Slugify.builder().build();
        String name = registerRequest.getFullName();
        String username = slugify.slugify(name);
        User user = User.builder()
                .username(username)
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(name)
                .role(Role.USER)
                .statusAccount(StatusAccount.ACTIVE)
                .isPremium(false)
                .avatarUrl("https://placehold.co/400")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    public Optional login(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional findById(Integer id) {
        return userRepository.findById(id);
    }
}
