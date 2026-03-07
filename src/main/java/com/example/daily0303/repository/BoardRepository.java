package com.example.daily0303.repository;


import com.example.daily0303.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
    List<Board> findByContentContaining(String keyword);
    List<Board> findByWriterContaining(String keyword);
}
