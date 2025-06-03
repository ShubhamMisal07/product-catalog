package com.example.productcatalog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConnectionChecker {

    @Bean
    public CommandLineRunner mongoconnectionChecker(MongoTemplate mongoTemplate) {
        return args -> {
            try {
                mongoTemplate.getDb().listCollectionNames().first();
                System.out.println("✅ MongoDB connected successfully.");
            } catch (Exception e) {
                System.err.println("❌ Failed to connect to MongoDB: " + e.getMessage());
            }
        };
    }
}
