package com.example.musicbackend.rest;

import com.example.musicbackend.config.AudioUtils;
import com.example.musicbackend.entity.Album;
import com.example.musicbackend.entity.Artist;
import com.example.musicbackend.entity.Song;
import com.example.musicbackend.model.Enum.SongStatus;
import com.example.musicbackend.repository.AlbumRepository;
import com.example.musicbackend.repository.ArtistRepository;
import com.example.musicbackend.repository.SongRepository;
import com.example.musicbackend.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload/song")
@RequiredArgsConstructor
public class UploadApi {

    private final CloudinaryService cloudinaryService;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    /**
     * Upload nhạc + ảnh cover + thông tin bài hát
     * POST http://localhost:8083/api/upload/song
     * <p>
     * Form-data:
     * - audioFile: file nhạc (mp3, wav)
     * - coverImage: file ảnh cover
     * - title: Tên bài hát
     * - artistId: ID nghệ sĩ
     * - albumId: ID album (optional)
     * - duration: Thời lượng (giây)
     */
    @PostMapping("/mp3")
    public ResponseEntity<?> uploadSong(
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam("title") String title,
            @RequestParam("artistId") Integer artistId,
            @RequestParam(value = "albumId", required = false) Integer albumId
//            @RequestParam(value = "duration", required = false) Integer duration
           ){
        try {
            int duration = AudioUtils.getMp3DurationInSeconds(audioFile);
            String mp3Url = cloudinaryService.uploadAudio(audioFile);
            String coverImageUrl = cloudinaryService.uploadImage(coverImage);
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));
            Album album = null;
            if (albumId != null) {
                album = albumRepository.findById(albumId).orElse(null);
            }

            Song song = Song.builder()
                    .title(title)
                    .artist(artist)
                    .album(album)
                    .duration(duration)
                    .fileUrl(mp3Url)
                    .coverImageUrl(coverImageUrl)
                    .status(SongStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .playCount(0)
                    .downloadCount(0)
                    .isPremium(false)
                    .build();
            songRepository.save(song);

            /// Trả về thông tin
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Upload thành công!");
            response.put("song", song);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload song: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

}
