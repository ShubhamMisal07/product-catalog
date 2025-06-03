package com.example.productcatalog.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Positive(message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places")
    private double price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    // Custom business logic methods
    public boolean isInStock() {
        return this.stockQuantity > 0;
    }

    public void decreaseStock(int quantity) {
        if (this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
        } else {
            throw new IllegalStateException("Insufficient stock available");
        }
    }

    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    }
}