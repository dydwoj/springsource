package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.service.BookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @ResponseBody
    @GetMapping("/create")
    public void getCreate(BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서 작성 폼 요청");
    }

    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO dto) {
        log.info("도서 작성 요청");

        Long code = bookService.insert(dto);
        return code;
    }

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book List 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify{code}" })
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO dto = bookService.read(code);
        return dto;
    }

    @ResponseBody
    @PutMapping("/modify")
    public Long postModify(@RequestBody BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("book modify 요청 {}", dto);
        bookService.modify(dto);

        return dto.getCode();
    }

    @ResponseBody
    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> postRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO) {
        log.info("book remove 요청 {}", code);
        bookService.remove(code);
        return new ResponseEntity<>(code, HttpStatus.OK);
    }

}
