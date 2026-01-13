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
@Table(name = "song_genres")
public class SongGenre {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    Genre genre;

    LocalDateTime genreDate;
}
