package com.demo.security.services;

import com.demo.security.dtos.LoginRequest;
import com.demo.security.dtos.RegisterRequest;
import com.demo.security.dtos.TokenResponse;
import com.demo.security.entities.Provider;
import com.demo.security.entities.Role;
import com.demo.security.entities.User;
import com.demo.security.repositories.RoleRepository;
import com.demo.security.repositories.UserRepository;
import com.demo.security.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        Role role = roleRepository.findByName(Role.USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.LOCAL)
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        String accessToken = tokenProvider.generateToken(savedUser);
        return TokenResponse.builder().accessToken(accessToken).build();
    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user =  userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = tokenProvider.generateToken(user);
        return TokenResponse.builder().accessToken(accessToken).build();
    }
}
