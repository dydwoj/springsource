package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.ToDo;
import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    // select 문을 대신할 메서드 생성
    // 완료/미완료 목록 추출
    List<ToDo> findByCompleted(boolean completed);

    // 중요/비중요 목록 추출
    List<ToDo> findByImportanted(boolean importanted);
}
