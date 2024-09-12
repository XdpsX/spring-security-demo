package com.demo.security.services;

import com.demo.security.dtos.UserProfile;
import com.demo.security.entities.User;
import com.demo.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfile getUserProfile(String email) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserProfile.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
