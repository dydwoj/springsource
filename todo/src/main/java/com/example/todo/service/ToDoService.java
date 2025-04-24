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
        // 리스트 가져오기
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

    // 체크박스 누르면 completed 값 변경
    public Long changeCompleted(ToDoDTO dto) {
        ToDo todo = toDoRepository.findById(dto.getId()).get();
        todo.setCompleted(dto.isCompleted());
        return toDoRepository.save(todo).getId();
    }

    public ToDoDTO read(Long id) {
        ToDo todo = toDoRepository.findById(id).get();
        // entity => dto 변경 후 리턴
        return modelMapper.map(todo, ToDoDTO.class);
    }

    public void remove(Long id) {
        toDoRepository.deleteById(id);
    }

    public Long create(ToDoDTO dto) {
        ToDo todo = modelMapper.map(dto, ToDo.class);
        return toDoRepository.save(todo).getId();
    }

}
