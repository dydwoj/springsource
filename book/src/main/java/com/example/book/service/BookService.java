package com.example.book.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public void insert(BookDTO dto) {
    }

    public void read(Long code) {
    }

    public void readAll() {
    }

    public void modify(BookDTO dto) {
    }

    public void remove(Long code) {
    }
}
