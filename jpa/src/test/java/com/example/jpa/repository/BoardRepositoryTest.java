package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .title("boardTitle test" + i)
                    .writer("boardWriter test" + i)
                    .content("boardContent test" + i)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void updateTest() {

        // Memo memo = Memo.builder().mno(1L).memoText("memoText update").build();
        Board board = boardRepository.findById(2L).get();
        board.setTitle("boardTitle update");
        boardRepository.save(board);
    }

    @Test
    public void readTest() {

        Board board = boardRepository.findById(2L).get();
        System.out.println(board);

    }

    @Test
    public void listTest() {

        boardRepository.findAll().forEach(board -> System.out.println(board));

    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(11L);
    }

}
