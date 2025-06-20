package com.example.todo.controller;

import java.util.List;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.service.ToDoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/todos")
@RequiredArgsConstructor
@Log4j2
@RestController
public class ToDoRestController {

    private final ToDoService toDoService;

    @GetMapping("")
    public List<ToDoDTO> getList() {
        log.info("전체 todo 가져오기");
        List<ToDoDTO> todos = toDoService.list2();
        return todos;
    }

    @PutMapping("/{id}")
    public Long postCompleted(@RequestBody ToDoDTO dto) {
        log.info("수정 {}", dto);
        Long id = toDoService.changeCompleted(dto);
        return id;
    }

    @DeleteMapping("/{id}")
    public String getRemove(@PathVariable Long id) {
        log.info("삭제 {}", id);

        toDoService.remove(id);
        return "success";
    }

    @PostMapping("/add")
    public ToDoDTO postCreate(@RequestBody ToDoDTO dto) {
        log.info("todo 입력 요청 {}", dto);
        ToDoDTO newDto = toDoService.create2(dto);
        return newDto;
    }

}
