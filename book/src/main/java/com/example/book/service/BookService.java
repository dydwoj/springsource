package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        // Book book = Book.builder()
        // .title(dto.getTitle())
        // .author(dto.getAuthor())
        // .price(dto.getPrice())
        // .build();
        // return bookRepository.save(book).getCode();

        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();
    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> readAll() {
        List<Book> list = bookRepository.findAll();
        List<BookDTO> books = list.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return books;
    }

    public Long modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        return bookRepository.save(book).getCode();
    }

    public void remove(Long code) {
        bookRepository.deleteById(code);
    }
}
