package com.example.musicbackend.rest;

import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.entity.Song;
import com.example.musicbackend.service.ArtistService;
import com.example.musicbackend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistApi {
    @Autowired
    private ArtistService artistService;
    @Autowired
    private SongService songService;
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{artistId}/songs")
    public List<Song> getSongsByArtist(@PathVariable Integer artistId) {
        return songService.getSongsByArtist(artistId);
    }
}
