package com.app.tiktok.auth;

import com.app.tiktok.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, jwtTokenUtil, passwordEncoder);
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setEmail("john@example.com");
        request.setPassword("123456");

        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(jwtTokenUtil.generateToken(any())).thenReturn("token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());
    }
}