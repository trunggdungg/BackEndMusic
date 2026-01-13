package com.example.musicbackend.repository;

import com.example.musicbackend.entity.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongGenreRepository extends JpaRepository<SongGenre, Integer> {
}
