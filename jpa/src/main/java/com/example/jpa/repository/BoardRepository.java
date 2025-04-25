package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // WHERE b.WRITER = 'boardWriter test4';
    // List<Board> findByWriter(String writer);

    // List<Board> findByTitle(String title);

    // // b.WRITER like 'user%'
    // List<Board> findByWriterStartingWith(String writer);

    // // b.WRITER like '%user'
    // List<Board> findByWriterEndingWith(String writer);

    // // b.WRITER like '%user%'
    // List<Board> findByWriterContaining(String writer);

    // // b.WRITER like '%user%' or b.content like '%내용%'
    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // // b.WRITER like '%user4%' and b.content like '%내용%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // // id > 5 게시물 조회
    // List<Board> findByBnoGreaterThan(Long bno);

    // // bno > 0 크면서 내림차순
    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10
    // // where bno between 5 and 10
    // List<Board> findByBnoBetween(Long start, Long end);

    // -------------------------------
    // @Query
    // -------------------------------

    // @Query("select b from Board b where b.writer = ?1")
    // List<Board> findByWriter(String writer);

    @Query("select b from Board b where b.writer like ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno > ?1")
    // @Query(value = "select * from Board b where b.bno > ?1", nativeQuery = true)
    @NativeQuery("select * from Board b where b.bno > ?1")
    List<Board> findByBnoGreaterThan(Long bno);

}
