package com.example.musicbackend.repository;

import com.example.musicbackend.entity.FollowArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowArtistRepository extends JpaRepository<FollowArtist, Integer> {
}
