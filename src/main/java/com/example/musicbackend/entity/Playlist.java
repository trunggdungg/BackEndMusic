package com.example.musicbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer playlistId;
    String name;
    String slug;
    String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    String imageUrl;
    Boolean isPublic;
    Integer totalSongs;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
