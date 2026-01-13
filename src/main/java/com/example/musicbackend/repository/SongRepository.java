package com.example.musicbackend.repository;

import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByArtist(Artist artist);
}
