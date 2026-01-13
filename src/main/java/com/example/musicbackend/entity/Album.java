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
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer albumId;
    String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    Artist artist;

    String coverImageUrl;
    String description;
    Integer totalSongs;
    LocalDateTime releaseDate;
    LocalDateTime createdAt;

}
