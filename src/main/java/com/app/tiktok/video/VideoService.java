package com.app.tiktok.video;

import com.app.tiktok.user.User;
import com.app.tiktok.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public VideoUploadResponse uploadVideo(String title, String description, MultipartFile file, UserDetailsImpl user) {
        log.info("Uploading video: {} by user: {}", title, user.getUsername());

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
        video.setUser(user.getUser()); // 🆕 gán user vào video
        video.setVideoUrl("/videos/" + filename);
        video.setCreatedAt(LocalDateTime.now());

        videoRepository.save(video);

        return new VideoUploadResponse(video.getId(), video.getTitle(), video.getVideoUrl());
    }

    public Page<VideoFeedResponse> getVideoFeed(int page, int size) {
        return videoRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .map(video -> new VideoFeedResponse(
                        video.getId(),
                        video.getTitle(),
                        video.getDescription(),
                        video.getVideoUrl(),
                        video.getCreatedAt(),
                        video.getUser().getUsername() // 🆕 thêm username người đăng
                ));
    }
}
