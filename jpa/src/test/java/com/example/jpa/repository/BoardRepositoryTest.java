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

    // crud
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .writer("user" + i)
                    .content("content" + i)
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

    // 쿼리 메서드 테스트
    @Test
    public void queryMethodTest() {
        // System.out.println(boardRepository.findByWriter("boardWriter test4"));
        // System.out.println(boardRepository.findByTitle("boardTitle test2"));

        // System.out.println(boardRepository.findByWriterStartingWith("user"));// user%
        // System.out.println(boardRepository.findByWriterEndingWith("user")); %user
        // System.out.println(boardRepository.findByWriterContaining("user"));//%user%

        // System.out.println(boardRepository.findByWriterContainingOrContentContaining("5",
        // "9"));
        // System.out.println(boardRepository.findByWriterContainingAndContentContaining("5",
        // "9"));

        // bno > 5
        // System.out.println(boardRepository.findByBnoGreaterThan(5L));
        // 내림차순 추가
        // System.out.println(boardRepository.findByBnoGreaterThanOrderByBnoDesc(0L));

        //
        // System.out.println(boardRepository.findByBnoBetween(5L, 10L));

        // ----------------------
        // 쿼리 어노테이션
        // ----------------------

        boardRepository.findByWriterStartingWith("user").forEach(i -> System.out.println(i));
        boardRepository.findByWriterContaining("user").forEach(i -> System.out.println(i));

    }

}
