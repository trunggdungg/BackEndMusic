package com.example.musicbackend.repository;

import com.example.musicbackend.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    List<Artist> findByNameContainingIgnoreCase(String name);
}
