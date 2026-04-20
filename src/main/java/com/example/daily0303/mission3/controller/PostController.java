package com.example.daily0303.mission3.controller;

import com.example.daily0303.mission3.domain.Post;
import com.example.daily0303.mission3.dto.PostRequestDto;
import com.example.daily0303.mission3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mission3/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String list(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "mission3/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "mission3/detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("postRequestDto", new PostRequestDto());
        return "mission3/form";
    }

    @PostMapping
    public String create(PostRequestDto dto) {
        Long id = postService.create(dto);
        return "redirect:/mission3/posts/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        PostRequestDto dto = new PostRequestDto();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        model.addAttribute("postRequestDto", dto);
        model.addAttribute("postId", id);
        return "mission3/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, PostRequestDto dto) {
        postService.update(id, dto);
        return "redirect:/mission3/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/mission3/posts";
    }
}
