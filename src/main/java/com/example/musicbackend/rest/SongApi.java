package com.example.musicbackend.rest;

import com.example.musicbackend.entity.Song;
import com.example.musicbackend.model.respone.DTO.SearchResultDTO;
import com.example.musicbackend.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongApi {

    private final SongService songService;

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResultDTO> searchResult(
            @RequestParam(value = "query") String keyword,
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        if (keyword == null || keyword.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SearchResultDTO resultDTO  = songService.searchResultDTO(keyword, limit);
        return ResponseEntity.ok(resultDTO);
    }


}
