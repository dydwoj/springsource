package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class StudentRepositoryTest {

    @Autowired // = new StudentRepository()
    private StudentRepository studentRepository;

    // CRUD test
    // Repository, Entity 확인

    @Test
    public void insertTest() {
        // Entity 생성

        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();
            // insert 구문
            studentRepository.save(student);
        });

    }

    @Test // 테스트 메서드임 (리턴 타입이 무조건 void)
    public void updateTest() {

        // findById(1L) : select * from 테이블명 where id = 1

        Student student = studentRepository.findById(1L).get();
        student.setGrade(Grade.SENIOR);

        // update 구문
        studentRepository.save(student);
    }

    @Test
    public void selectOneTest() {
        // Optional<Student> student = studentRepository.findById(1L);

        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }
        // }

        // Student student = studentRepository.findById(3L).get();
        // EntityNotFoundException
        Student student = studentRepository.findById(3L).orElseThrow(EntityNotFoundException::new);
        System.out.println(student);
    }

    @Test
    public void selectTest() {

        // 전체 일반적이 조회
        // List<Student> list = studentRepository.findAll();

        // for (Student student : list) {
        // System.out.println(student);
        // }

        // 전체 람다식 조회
        studentRepository.findAll().forEach(student -> System.out.println(student));
    }

    @Test
    public void deleteTest() {

        // Student student = studentRepository.findById(11L).get();
        // studentRepository.delete(student);

        studentRepository.deleteById(10L);

    }

}
