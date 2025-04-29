package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid BookDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("도서 작성 요청");

        if (result.hasErrors()) {
            return "/book/create";
        }
        Long code = bookService.insert(dto);
        rttr.addFlashAttribute("code", code);
        return "redirect:/book/list";
    }

    // 1. 리스트만 만들었을 때 : 들어와야 하는 주소 : http://localhost:8080/book/list?page=1&size=10
    // 2. 검색 기능 만들었을 때 : 들어와야 하는 주소 :
    // http://localhost:8080/book/list?page=1&size=10&type=t&keword=Witcher
    // 검색기능에서 page, size 값을 가지고 가는 방법
    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book List 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        model.addAttribute("result", pageResultDTO);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO dto = bookService.read(code);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);
        Long code = bookService.modify(dto);

        rttr.addAttribute("code", code);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book remove 요청 {}", code);
        bookService.remove(code);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/list";
    }

}
