package com.example.musicbackend.repository;

import com.example.musicbackend.entity.ListeningHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Integer> {
}
