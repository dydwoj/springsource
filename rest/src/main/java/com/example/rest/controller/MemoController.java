package com.example.rest.controller;

import java.util.List;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.MemoDTO;
import com.example.rest.service.MemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/memo")
@Log4j2
@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getMemoList(Model model) {
        List<MemoDTO> list = memoService.getList();
        return list;
    }

    @GetMapping({ "/read", "/update" })
    public MemoDTO getMemoRow(Long mno, Model model) {
        log.info("조회 요청 {}", mno);
        MemoDTO dto = memoService.getRow(mno);
        return dto;
    }

    @PutMapping("/update")
    public Long postMemoUpdate(@RequestBody MemoDTO dto) {
        log.info("메모 수정 요청 {}", dto);
        // 수정 요청
        Long mno = memoService.memoUpdate(dto);
        return mno;
    }

    @GetMapping("/new")
    public void getMemoNew() {
        log.info("새 메모 작성 폼 요청");
    }

    @PostMapping("/new")
    public Long postMemoNew(@RequestBody MemoDTO dto) {
        log.info("새 메모 작성 {}", dto);
        Long mno = memoService.memoCreate(dto);
        return mno;
    }

    // memo 삭제 : /memo/remove/3
    @DeleteMapping("/remove/{mno}")
    public Long getMemoRemove(@PathVariable Long mno) {
        log.info("memo 삭제 요청 {}", mno);

        // 삭제요청
        memoService.memoDelete(mno);
        return mno;
    }
}
