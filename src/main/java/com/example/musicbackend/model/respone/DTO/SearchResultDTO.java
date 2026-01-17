package com.example.musicbackend.model.respone.DTO;

import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.entity.Song;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultDTO {
    private List<Song> songs;
    private List<Artist> artists;
    private int totalSongs;
    private int totalArtists;
}
