package com.example.musicbackend.rest;


import com.example.musicbackend.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteApi {
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllFavorites(@PathVariable Integer id) {
        return ResponseEntity.ok(
                favoriteService.getFavoriteByUserId(id)
        );
    }
}
