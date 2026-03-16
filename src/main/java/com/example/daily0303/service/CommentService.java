package com.example.daily0303.service;

import com.example.daily0303.domain.Comment;
import com.example.daily0303.domain.PostRepository;
import com.example.daily0303.domain.Post;
import com.example.daily0303.dto.CommentRequestDto;
import com.example.daily0303.domain.*;
import com.example.daily0303.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveComment(Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .post(post)
                .build();

        return commentRepository.save(comment).getId();
    }

    public List<CommentResponseDto> findAllByPost(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        comment.update(requestDto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
