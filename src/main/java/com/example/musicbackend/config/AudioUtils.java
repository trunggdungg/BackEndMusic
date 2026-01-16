package com.example.musicbackend.config;

import com.mpatric.mp3agic.Mp3File;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

public class AudioUtils {

    public static int getMp3DurationInSeconds(MultipartFile multipartFile) throws Exception {

        // ✅ Tạo file tạm với ĐÚNG đuôi .mp3
        File tempMp3 = File.createTempFile("upload_", ".mp3");

        // Copy data sang file .mp3
        Files.write(tempMp3.toPath(), multipartFile.getBytes());

        Mp3File mp3File = new Mp3File(tempMp3);
        int duration = (int) mp3File.getLengthInSeconds();

        // cleanup
        tempMp3.delete();

        return duration;
    }
}
