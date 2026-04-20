package com.example.daily0303.mission2.dto;

import com.example.daily0303.mission2.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
