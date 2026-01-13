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
@Table(name = "playlist_songs")
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "song_id")
    Song song;

    Integer position;
    LocalDateTime addedAt;
}
