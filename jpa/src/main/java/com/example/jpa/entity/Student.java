package com.example.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Table(name = "studenttbl")
@Entity // => DB TABLE 과 동일해짐
public class Student {

    @Id
    @SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")
    // @GeneratedValue => create sequence studenttbl_seq start with 1 increment by
    // 50
    private Long id; // => id number(19,0) not null / primary key (id)
    private String name; // => name varchar2(255 char) / null 허용

}
