package com.example.productcatalog.config;

import com.example.productcatalog.model.UserEntity;
import com.example.productcatalog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                UserEntity admin = new UserEntity("admin", passwordEncoder.encode("admin123"), List.of("ADMIN", "USER"));
                UserEntity user = new UserEntity("user", passwordEncoder.encode("user123"), List.of("USER"));
                userRepository.saveAll(List.of(admin, user));
                System.out.println("✅ Seeded initial users.");
            }
        };
    }
}
