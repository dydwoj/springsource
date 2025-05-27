package com.example.novels.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNovelRepository {

    // 하나 조회
    Object[] getNovelByID(Long id);

    // 페이지 나누기 + 조회
    Page<Object[]> list(Pageable pageable);

}
