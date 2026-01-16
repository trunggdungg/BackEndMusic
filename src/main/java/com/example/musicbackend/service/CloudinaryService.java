package com.example.musicbackend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadAudio(MultipartFile file) throws IOException {
        // Upload vào folder "music_app/audio" trên Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video", // Audio được lưu dưới dạng video
                        "folder", "music_app/audio"
                )
        );
        System.out.println("=== CLOUDINARY RESPONSE ===");
        uploadResult.forEach((k, v) -> System.out.println(k + " : " + v));
        return uploadResult.get("secure_url").toString();
    }

    /**
     * Upload ảnh cover lên Cloudinary
     *
     * @param file - File ảnh (jpg, png, etc.)
     * @return URL của ảnh đã upload
     */
    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "music_app/images"
                )
        );

        return uploadResult.get("secure_url").toString();
    }

    /**
     * Xóa file trên Cloudinary
     *
     * @param publicId - ID của file (lấy từ URL)
     */
    public void deleteFile(String publicId, String resourceType) throws IOException {
        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap("resource_type", resourceType)
        );
    }



}
