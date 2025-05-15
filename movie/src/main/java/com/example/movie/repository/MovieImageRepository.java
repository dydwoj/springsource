package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.total.MovieImageReviewRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long>, MovieImageReviewRepository {

    // movie 번호 기준으로 이미지 제거

    @Modifying // delete, update 시 반드시 작성
    @Query("DELETE FROM MovieImage mi WHERE mi.movie = :movie")
    void deleteByMovie(@Param("movie") Movie movie);

}
