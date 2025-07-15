package com.app.tiktok.comment;

import com.app.tiktok.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByVideoOrderByCreatedAtDesc(Video video);
}
