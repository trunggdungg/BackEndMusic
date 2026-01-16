package com.example.musicbackend.repository;

import com.example.musicbackend.entity.Favorite;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

  List<Favorite> findByUserId(Integer id, Sort createdAt);
}
