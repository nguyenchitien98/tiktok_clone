package com.app.tiktok.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
