package com.example.musicbackend;

import com.example.musicbackend.entity.Song;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicBackendApplication {
    static final Song SONG = new Song();
    public static void main(String[] args) {
        SpringApplication.run(MusicBackendApplication.class, args);
        SONG.setId(1);
        System.out.printf("Song ID: %d%n", SONG.getId());
    }

}
