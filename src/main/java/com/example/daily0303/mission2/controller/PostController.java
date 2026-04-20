package com.example.daily0303.mission2.controller;

import com.example.daily0303.mission2.dto.PostRequestDto;
import com.example.daily0303.mission2.dto.PostResponseDto;
import com.example.daily0303.mission2.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id,
                                                   @Valid @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
