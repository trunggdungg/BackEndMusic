package com.example.musicbackend.rest;


import com.example.musicbackend.entity.Favorite;
import com.example.musicbackend.model.request.AddFavoriteRequest;
import com.example.musicbackend.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody AddFavoriteRequest request) {
        try {
            Favorite favorite = favoriteService.addFavorite(
                    request.getUserId(),
                    request.getSongId()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Đã thêm vào danh sách yêu thích");
            response.put("favorite", favorite);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{userId}/{songId}")
    public ResponseEntity<?> deleteFavorite(@PathVariable Integer userId, @PathVariable Integer songId) {
        try {
            favoriteService.removeFavorite(userId, songId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Đã xóa khỏi danh sách yêu thích");
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{userId}/check/{songId}")
    public ResponseEntity<?> checkFavorite(
            @PathVariable Integer userId,
            @PathVariable Integer songId
    ) {
        try {
            boolean isFavorite = favoriteService.isFavorite(userId, songId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("isFavorite", isFavorite);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
