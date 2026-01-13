package com.example.musicbackend.entity;

import com.example.musicbackend.model.Enum.SongStatus;
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
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    Album album;

    String slug;
    Integer duration; // duration in seconds
    String fileUrl;
    String coverImageUrl;
    LocalDateTime releaseDate;
    /// dạng text
    @Lob
    @Column(columnDefinition = "TEXT")
    String lyrics;
    Integer playCount;
    Integer downloadCount;
    Boolean isPremium;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    SongStatus status;

}
