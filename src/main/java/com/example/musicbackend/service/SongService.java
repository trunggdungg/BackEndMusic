package com.example.musicbackend.service;

import com.example.musicbackend.entity.Song;
import com.example.musicbackend.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }

    public List<Song> getSongsByArtist(Integer artistId) {
        return songRepository.findByArtist_ArtistId(artistId);
    }
}
