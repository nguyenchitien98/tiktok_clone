package com.app.tiktok.comment;

import com.app.tiktok.common.response.ApiResponse;
import com.app.tiktok.user.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @RequestBody @Valid CreateCommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails // 🆕 lấy user từ token
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                commentService.createComment(request, userDetails.getUser())
        ));
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsByVideo(@PathVariable Long videoId) {
        return ResponseEntity.ok(ApiResponse.success(
                commentService.getCommentsByVideo(videoId)
        ));
    }
}
