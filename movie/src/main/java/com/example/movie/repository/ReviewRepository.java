package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // movie 번호 기준으로 이미지 제거

    @Modifying // delete, update 시 반드시 작성
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(@Param("movie") Movie movie);

}
