package com.example.musicbackend.service;

import com.example.musicbackend.entity.Favorite;
import com.example.musicbackend.entity.Song;
import com.example.musicbackend.entity.User;
import com.example.musicbackend.model.respone.exception.UserNotFoundException;
import com.example.musicbackend.repository.FavoriteRepository;
import com.example.musicbackend.repository.SongRepository;
import com.example.musicbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;
    private final SongRepository songRepository;

    public List<Favorite> getFavoriteByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return favoriteRepository.findByUserId(
                userId,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }

    public Favorite addFavorite(Integer userId, Integer songId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );

        Song song = songRepository.findById(songId).orElseThrow(
                () -> new RuntimeException("Song not found with id: " + songId)
        );

        if (favoriteRepository.existsByUserIdAndSongId(userId, songId)) {
            throw new IllegalArgumentException("Song is already in favorites");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .song(song)
                .createdAt(LocalDateTime.now())
                .build();

        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Integer userId, Integer songId) {
        Favorite favorite = favoriteRepository.findByUserIdAndSongId(userId, songId)
                .orElseThrow(() -> new RuntimeException("Bài hát không có trong danh sách yêu thích"));

        favoriteRepository.delete(favorite);
    }

    public boolean isFavorite(Integer userId, Integer songId) {
        return favoriteRepository.existsByUserIdAndSongId(userId, songId);
    }
}
