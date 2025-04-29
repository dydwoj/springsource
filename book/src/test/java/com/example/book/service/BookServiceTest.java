package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void listAllTest() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, "t", "Witcher");
        PageResultDTO<BookDTO> result = bookService.readAll(pageRequestDTO);

        System.out.println("내용");
        System.out.println(result.getDtoList());
        System.out.println("페이지 나누기 정보");
        System.out.println("TotalPage : " + result.getTotalPage());
        System.out.println("PageNumList : " + result.getPageNumList());
        System.out.println("next 여부 : " + result.isNext());
        System.out.println("prev 여부 : " + result.isPrev());
    }
}
