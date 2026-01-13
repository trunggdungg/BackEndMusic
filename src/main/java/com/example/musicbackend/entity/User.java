package com.example.musicbackend.entity;

import com.example.musicbackend.model.Enum.Gender;
import com.example.musicbackend.model.Enum.Role;
import com.example.musicbackend.model.Enum.StatusAccount;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String username;
    String email;
    String password;
    String fullName;
    String avatarUrl;
    String phone;
    LocalDateTime dob;
    Gender gender;
    Role role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    StatusAccount statusAccount;
    Boolean isPremium;
}
