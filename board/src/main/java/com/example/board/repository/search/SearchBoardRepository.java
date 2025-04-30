package com.example.board.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Page<Object[]> list(Pageable pageable);

    Object[] getBoardByBno(Long bno);

}
