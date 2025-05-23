package com.example.book.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequestMapping("/books")
@RestController
@Log4j2
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("")
    public PageResultDTO<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("book List 요청 {}", pageRequestDTO);
        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        return pageResultDTO;
    }

}
