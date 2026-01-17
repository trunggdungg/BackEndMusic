package com.example.musicbackend.service;

import com.example.musicbackend.entity.Favorite;
import com.example.musicbackend.model.respone.exception.UserNotFoundException;
import com.example.musicbackend.repository.FavoriteRepository;
import com.example.musicbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Favorite> getFavoriteByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        return favoriteRepository.findByUserId(
                userId,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}
