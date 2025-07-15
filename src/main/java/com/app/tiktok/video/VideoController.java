package com.app.tiktok.video;

import com.app.tiktok.common.response.ApiResponse;
import com.app.tiktok.user.User;
import com.app.tiktok.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<VideoUploadResponse>> upload(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl currentUser
    ) {
        return ResponseEntity.ok(ApiResponse.success(videoService.uploadVideo(title, description, file, currentUser)));
    }

    @GetMapping("/feed")
    public ResponseEntity<ApiResponse<Page<VideoFeedResponse>>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(videoService.getVideoFeed(page, size)));
    }
}
