package com.example.musicbackend.service;

import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }


}
