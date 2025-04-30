package com.example.board.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    // Function<T, R> : T => R 로 변환

    public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.list(pageable);
        Function<Object[], BoardDTO> fn = (entity -> entityToDto((Board) entity[0], (Member) entity[1],
                (Long) entity[2]));
        List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());

        Long totalCount = result.getTotalElements();

        PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResultDTO;
    }

    public BoardDTO getRow(Long bno) {
        Object[] entity = boardRepository.getBoardByBno(bno);
        return entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]);
    }

    // ModelMapper 를 사용 할 수 없기에 따로 변환하는 메서드를 만듬
    private BoardDTO entityToDto(Board board, Member member, Long replyCount) {
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .email(member.getEmail())
                .name(member.getName())
                .replyCount(replyCount)
                .createdDate(board.getCreatedDate())
                .build();
        return dto;
    }

}
