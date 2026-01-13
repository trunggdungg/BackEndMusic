package com.example.musicbackend.repository;

import com.example.musicbackend.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Integer> {
}
