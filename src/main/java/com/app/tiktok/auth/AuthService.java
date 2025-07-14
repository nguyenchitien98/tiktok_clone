package com.app.tiktok.auth;

import com.app.tiktok.common.exception.BadRequestException;
import com.app.tiktok.user.User;
import com.app.tiktok.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthResponse register(RegisterRequest request) {
        log.info("Registering user: {}", request.getUsername());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        log.info("Logging in user: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtTokenUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
