package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(Model model, PageRequestDTO pageRequestDTO) {
        log.info("list 요청 {}", pageRequestDTO);

        PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long bno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("read 요청 / get {}", bno);

        BoardDTO dto = boardService.getRow(bno);
        model.addAttribute("dto", dto);
    }

    @PreAuthorize("authentication.name == #dto.email")
    @PostMapping("/modify")
    public String postUpdate(BoardDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("수정 : {} {}", dto, pageRequestDTO);

        Long bno = boardService.update(dto);
        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        // 수정 완료 후 read 이동
        return "redirect:/board/read";
    }

    @PreAuthorize("authentication.name == #email")
    @PostMapping({ "/remove" })
    public String getDelete(Long bno, String email, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("remove 요청 / get {}", bno);

        // 삭제
        boardService.delete(bno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        // 수정 완료 후 read 이동
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public void getMethodName(@ModelAttribute("dto") BoardDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("글 작성 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid BoardDTO dto, BindingResult result,
            PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("글 작성 요청 : {}", dto);

        // 유효성 검증 후 결과 확인
        if (result.hasErrors()) {
            return "/board/create";
        }

        // 서비스 호출
        boardService.create(dto);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/board/list";
    }

}
