package com.example.productcatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.productcatalog.security.CustomUserDetailsService;
import com.example.productcatalog.security.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder; // ADD THIS
    
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil ,CustomUserDetailsService customUserDetailsService ,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println("⚡ login endpoint hit");  // Debugging log

        try{
            // ✅ Load user first for debug
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
            System.out.println("🔐 Hashed password from DB: " + userDetails.getPassword());
            System.out.println("🔐 Raw password from request: " + authRequest.getPassword());
            boolean match = passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword());
            System.out.println("🧪 Password match: " + match);

            if (!match) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
            }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword())
        );
        
        System.out.println("✅ Authentication success for " + authRequest.getUsername());

        // UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        // String token = jwtUtil.generateToken(authRequest.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }catch (Exception e) {
        System.out.println("❌ Authentication failed: " + e.getMessage());
        return ResponseEntity
                .status(401) // Return 401 Unauthorized instead of 403
                .body(Map.of("error", "Invalid username or password"));
    }
}

    @GetMapping("/ping")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("pong");
    }

    public static class AuthRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
