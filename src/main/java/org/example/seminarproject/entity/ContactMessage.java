package org.example.seminarproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter your name.")
    @Size(max = 100, message = "Name must not exceed 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Please enter your email.")
    @Email(message = "Please enter a valid email address.")
    @Size(max = 254, message = "Email must not exceed 254 characters.")
    @Column(nullable = false, length = 254)
    private String email;

    @NotBlank(message = "Please enter a message.")
    @Size(max = 5000, message = "Message must not exceed 5000 characters.")
    @Column(nullable = false, length = 5000)
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ContactMessage() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}