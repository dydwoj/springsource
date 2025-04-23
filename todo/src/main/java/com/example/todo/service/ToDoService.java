package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.ToDoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    private final ModelMapper modelMapper;

    public List<ToDoDTO> list(boolean completed) {
        List<ToDo> list = toDoRepository.findByCompleted(completed);
        // ToDo entity => ToDoDTO 변경 후 리턴

        // 방법 1
        // List<ToDoDTO> todos = new ArrayList<>();
        // list.forEach(todo -> {
        // ToDoDTO dto = modelMapper.map(todo, ToDoDTO.class);
        // todos.add(dto);
        // });

        // 방법 2
        List<ToDoDTO> todos = list.stream()
                .map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());
        return todos;
    }

}
