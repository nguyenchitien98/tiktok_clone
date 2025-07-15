package com.app.tiktok.video;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoUploadResponse {
    private Long id;
    private String title;
    private String videoUrl;
}
