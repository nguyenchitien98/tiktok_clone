package com.app.tiktok.comment;

import com.app.tiktok.user.User;
import com.app.tiktok.video.Video;
import com.app.tiktok.video.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    public CommentResponse createComment(CreateCommentRequest request, User user) {
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setVideo(video);

        commentRepository.save(comment);

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                user.getUsername(),
                comment.getCreatedAt()
        );
    }

    public List<CommentResponse> getCommentsByVideo(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        return commentRepository.findByVideoOrderByCreatedAtDesc(video)
                .stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getCreatedAt()
                )).toList();
    }
}

