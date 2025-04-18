package com.example.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoService {
    // repository 메서드 호출하는 역할

    private final MemoRepository memoRepository;

    public List<MemoDTO> getList() {
        List<Memo> list = memoRepository.findAll();

        // Memo => MemoDTO 옮기기
        // List<MemoDTO> memos = new ArrayList<>();
        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);
        // }

        // list.stream().forEach(memo -> System.out.println(memo));

        List<MemoDTO> memos = list.stream()
                .map(memo -> entityToDTO(memo))
                .collect(Collectors.toList());
        return memos;
    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity => dto
        MemoDTO dto = entityToDTO(memo);
        return dto;
    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());
        // update 실행 => 수정된 Memo return
        memoRepository.save(memo);
        return memo.getMno();
    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할 memo는 MemoDTO 에 저장
        // MemoDTO => Memo 변환
        Memo memo = dtoToEntity(dto);
        // 새로 저장한 memo 리턴
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private Memo dtoToEntity(MemoDTO memoDTO) {
        Memo memo = Memo.builder()
                .mno(memoDTO.getMno())
                .memoText(memoDTO.getMemoText())
                .build();
        return memo;
    }

    private MemoDTO entityToDTO(Memo memo) {

        // 객체 선언 방식
        // MemoDTO dto = new MemoDTO();
        // dto.setMno(memo.getMno());
        // dto.setMemoText(memo.getMemoText());

        // builder 방식
        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updatedDate(memo.getUpdatedDate())
                .build();

        return dto;
    }

}
