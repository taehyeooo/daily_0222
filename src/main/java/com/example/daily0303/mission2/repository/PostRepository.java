package com.example.daily0303.mission2.repository;

import com.example.daily0303.mission2.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
