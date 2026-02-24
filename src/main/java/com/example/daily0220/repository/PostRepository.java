package com.example.daily0220.repository;

import com.example.daily0220.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContainingOrContentContainingOrAuthorContaining(String title, String content, String author);
}
