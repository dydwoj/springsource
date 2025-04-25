package com.example.book.controller;

import java.util.List;

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
    public void getCreate(@ModelAttribute("dto") BookDTO dto) {
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

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("book List 요청");
        List<BookDTO> list = bookService.readAll();
        model.addAttribute("list", list);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO dto = bookService.read(code);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);
        Long code = bookService.modify(dto);
        rttr.addAttribute("code", code);
        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code) {
        log.info("book remove 요청 {}", code);
        bookService.remove(code);
        return "redirect:/book/list";
    }

}
