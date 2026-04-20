package com.example.daily0303.mission2.service;

import com.example.daily0303.mission2.domain.Post;
import com.example.daily0303.mission2.dto.PostRequestDto;
import com.example.daily0303.mission2.dto.PostResponseDto;
import com.example.daily0303.mission2.exception.PostNotFoundException;
import com.example.daily0303.mission2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto create(PostRequestDto dto) {
        Post post = new Post(dto.getTitle(), dto.getContent(), dto.getAuthor());
        return new PostResponseDto(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        post.update(dto.getTitle(), dto.getContent());
        return new PostResponseDto(post);
    }

    @Transactional
    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }
}
