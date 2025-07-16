package com.example.elastic1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic1.dto.UserDocumentDTO;
import com.example.elastic1.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // http://localhost:8080/users + GET : 전체조회 => http://localhost:9200/
    // http://localhost:8080/users/1 + GET : 1번 아이디 조회
    // http://localhost:8080/users/1 + PUT : 1번 아이디 수정
    // http://localhost:8080/users/1 + DELETE : 1번 아이디 삭제

    private final UserService userService;

    @GetMapping("")
    public List<UserDocumentDTO> getList() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public UserDocumentDTO getRow(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserDocumentDTO putMethodName(@PathVariable String id, @RequestBody UserDocumentDTO userDocumentDTO) {
        userDocumentDTO.setId(id);
        return userService.updateUser(userDocumentDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteRow(@PathVariable String id) {
        userService.removeUser(id);
        return id;
    }

}
