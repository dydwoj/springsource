package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 37).forEach(i -> {
            Book book = Book.builder()
                    .title("The Witcher Chapter." + i)
                    .author("Andrzej Sapkowski")
                    .price(13500 + (i * 480))
                    .build();
            bookRepository.save(book);
        });
    }

    @Test
    public void testList() {
        bookRepository.findAll().forEach(book -> System.out.println(book));
    }

    @Test
    public void testGet() {
        Book book = bookRepository.findById(1L).get();
        System.out.println(book);
    }

    @Test
    public void testUpdate() {
        Book book = bookRepository.findById(3L).get();
        book.setPrice(16700);
        bookRepository.save(book);
    }

    @Test
    public void testRemove() {
        bookRepository.deleteById(20L);
    }

}
