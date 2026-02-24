package com.example.daily0220;

import com.example.daily0220.domain.Post;
import com.example.daily0220.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Daily0220Application implements CommandLineRunner {

    @Autowired
    private PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(Daily0220Application.class, args);
    }

    @Override
    public void run(String... args) {

        postRepository.save(new Post(null, "스프링 부트 시작하기", "내용입니다.", "김태형", null));
        postRepository.save(new Post(null, "자바 공부법", "스프링이 핵심.", "김땡떙", null));
        postRepository.save(new Post(null, "인텔리제이 꿀팁", "김태형님이 알려주신 팁", "홍길동", null));


        String keyword = "김태형";

        System.out.println("\n=== [" + keyword + "] 키워드 검색 결과 ===");


        List<Post> searchResults = postRepository.findByTitleContainingOrContentContainingOrAuthorContaining(keyword, keyword, keyword);

        if (searchResults.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            searchResults.forEach(p ->
                    System.out.println("제목: " + p.getTitle() + " | 작성자: " + p.getAuthor() + " | 내용: " + p.getContent()));
        }
    }
}