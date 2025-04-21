package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.service.MemoService;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/memo")
@Log4j2
@Controller
@RequiredArgsConstructor
public class MemoController {
    // 서비스 메서드 호출
    // 데이터가 전송된다면 전송된 데이터를 Model 에 담기
    private final MemoService memoService;

    // 경로(주소) 설계
    // 전체 memo 조회 : /memo/list
    @GetMapping("/list")
    public void getMemoList(Model model) {
        List<MemoDTO> list = memoService.getList();
        model.addAttribute("list", list);
    }

    // 특정 memo 조회 : /memo/read?mno=3
    @GetMapping({ "/read", "/update" })
    public void getMemoRow(Long mno, Model model) {
        log.info("조회 요청 {}", mno);
        MemoDTO dto = memoService.getRow(mno);
        model.addAttribute("dto", dto);
        // template 찾으러 감
    }

    // 특정 memo 수정 : /memo/update?mno=3
    @PostMapping("/update")
    public String postMemoUpdate(MemoDTO dto, RedirectAttributes rttr) {
        log.info("메모 수정 요청 {}", dto);
        // 수정 요청
        Long mno = memoService.memoUpdate(dto);
        // 수정완료시, read 화면 이동
        rttr.addAttribute("mno", mno);
        return "redirect:/memo/read";
    }

    // memo 추가 : /memo/new
    @GetMapping("/new")
    public void getMemoNew() {
        log.info("새 메모 작성 폼 요청");
    }

    @PostMapping("/new")
    public String postMemoNew(MemoDTO dto, RedirectAttributes rttr) {
        log.info("새 메모 작성 {}", dto);
        // 사용자 입력값 가져오기
        Long mno = memoService.memoCreate(dto);
        // 페이지 이동
        rttr.addFlashAttribute("msg", mno);
        return "redirect:/memo/list";
    }

    // memo 삭제 : /memo/remove?mno=3
    @GetMapping("/remove")
    public String getMemoRemove(Long mno) {
        log.info("memo 삭제 요청 {}", mno);

        // 삭제요청
        memoService.memoDelete(mno);
        return "redirect:/memo/list";
    }
}
