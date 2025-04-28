package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Board;
import com.example.jpa.entity.QBoard;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    // crud
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("board title" + i)
                    .writer("board user" + i)
                    .content("board content" + i)
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

        // boardRepository.findAll().forEach(board -> System.out.println(board));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        boardRepository.findAll(pageable).forEach(board -> System.out.println(board));

    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(11L);
    }

    // 쿼리 메서드 테스트
    @Test
    public void queryMethodTest() {
        System.out.println(boardRepository.findByWriter("user4"));
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

        // boardRepository.findByWriterStartingWith("user").forEach(i ->
        // System.out.println(i));
        // boardRepository.findByWriterContaining("user").forEach(i ->
        // System.out.println(i));

        List<Object[]> result = boardRepository.findByTitle2("title");
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
            String title = (String) objects[0];
            String writer = (String) objects[1];
            System.out.println("title : " + title + " wirter : " + writer);
            System.out.println("============================");
        }

    }

    @Test
    public void QueryDslTest() {

        // Q 파일 사용
        QBoard board = QBoard.board;

        // where b.title = 'title1'
        // System.out.println(boardRepository.findAll(board.title.eq("board title1")));

        // where b.title like 'title1%'
        // System.out.println(boardRepository.findAll(board.title.startsWith("board")));

        // where b.title like '%title1'
        // System.out.println(boardRepository.findAll(board.title.endsWith("title1")));

        // where b.title like '%title1%'
        System.out.println(boardRepository.findAll(board.title.contains("title1")));

        // where b.title like '%title1%' and b.bno > 0 order by bno desc
        boardRepository.findAll(board.title.contains("title1")
                .and(board.bno.gt(0L)), Sort.by("bno").descending())
                .forEach(i -> System.out.println(i));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").ascending());
        Page<Board> result = boardRepository.findAll(board.bno.gt(0L), pageable);
        System.out.println("page size : " + result.getSize());
        System.out.println("page Total Pages : " + result.getTotalPages());
        System.out.println("page Total Elements : " + result.getTotalElements());
        System.out.println("page Content : " + result.getContent());

    }

}
