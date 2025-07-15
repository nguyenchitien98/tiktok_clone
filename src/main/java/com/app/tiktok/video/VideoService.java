package com.app.tiktok.video;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    private final VideoRepository videoRepository;
    private static final String VIDEO_UPLOAD_DIR = "uploads/videos/";

    public VideoUploadResponse uploadVideo(String title, String description, MultipartFile file) {
        log.info("Uploading video: {}", title);

        String uploadDir =  System.getProperty("user.dir") + fileUploadDir;
        String filename = file.getOriginalFilename();
        Path filepath = Paths.get(uploadDir, filename);
        System.out.println(filepath);

        try {
            Files.createDirectories(filepath.getParent());
            file.transferTo(filepath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload video", e);
        }

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoUrl("/videos/" + filename);
        video.setCreatedAt(LocalDateTime.now());

        videoRepository.save(video);

        return new VideoUploadResponse(video.getId(), video.getTitle(), video.getVideoUrl());
    }
}
