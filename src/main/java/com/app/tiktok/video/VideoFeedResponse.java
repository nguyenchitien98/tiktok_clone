package com.app.tiktok.video;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VideoFeedResponse {
    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private LocalDateTime createdAt;
    private String username;
}
