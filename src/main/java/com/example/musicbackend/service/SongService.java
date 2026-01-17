package com.example.musicbackend.service;

import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.entity.Song;
import com.example.musicbackend.model.respone.DTO.SearchResultDTO;
import com.example.musicbackend.repository.ArtistRepository;
import com.example.musicbackend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final ArtistRepository artistRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }

    public List<Song> getSongsByArtist(Integer artistId) {
        return songRepository.findByArtist_ArtistId(artistId);
    }

    public SearchResultDTO searchResultDTO(String keyword, Integer limit) {
        List<Song> songs = songRepository.findByTitleContainingIgnoreCase(keyword);
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(keyword);
        List<Song> limitedSongs = songs.stream()
                .limit(limit)
                .toList();

        List<Artist> limitedArtists = artists.stream()
                .limit(limit)
                .toList();
        return SearchResultDTO.builder()
                .songs(limitedSongs)
                .artists(limitedArtists)
                .totalSongs(songs.size())
                .totalArtists(artists.size())
                .build();
    }
}
