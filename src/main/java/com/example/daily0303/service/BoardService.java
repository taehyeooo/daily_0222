package com.example.daily0303.service;


import com.example.daily0303.entity.Board;
import com.example.daily0303.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> searchPosts(String searchType, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return boardRepository.findAll();
        }

        return switch (searchType) {
            case "title" -> boardRepository.findByTitleContaining(keyword);
            case "content" -> boardRepository.findByContentContaining(keyword);
            case "writer" -> boardRepository.findByWriterContaining(keyword);
            default -> boardRepository.findAll();
        };
    }
}
