package com.filemanager.filemanager.controller;

import com.filemanager.filemanager.login.LoginRequest;
import com.filemanager.filemanager.login.LoginResponse;
import com.filemanager.filemanager.model.User;
import com.filemanager.filemanager.register.RegisterRequest;
import com.filemanager.filemanager.security.JwtTokenProvider;
import com.filemanager.filemanager.services.UserService;
import com.filemanager.filemanager.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse(token));
        }
        catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(registerRequest.getUsername());
        userService.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        );
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
