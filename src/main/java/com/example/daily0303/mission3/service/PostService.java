package com.example.daily0303.mission3.service;

import com.example.daily0303.mission3.domain.Post;
import com.example.daily0303.mission3.dto.PostRequestDto;
import com.example.daily0303.mission3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long create(PostRequestDto dto) {
        Post post = new Post(dto.getTitle(), dto.getContent(), dto.getAuthor());
        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));
    }

    @Transactional
    public void update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. id: " + id));
        post.update(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
