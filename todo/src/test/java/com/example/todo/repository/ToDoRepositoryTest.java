package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void toDoInsertTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ToDo toDo = ToDo.builder()
                    .content("toDo Insert Test " + i)
                    .build();
            toDoRepository.save(toDo);
        });
    }

    @Test
    public void toDoReadTest() {
        toDoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    @Test
    public void toDoDeleteTest() {
        toDoRepository.deleteById(3L);
    }

    @Test
    public void toDoUpdateTest() {
        ToDo todo = toDoRepository.findById(1L).get();
        todo.setCompleted(true);
        toDoRepository.save(todo);
    }

    @Test
    public void toDoReadTest2() {
        // 완료 목록 추출
        toDoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));

    }

    @Test
    public void toDoReadTest3() {
        // 비중요 목록 추출
        toDoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));
    }

}
