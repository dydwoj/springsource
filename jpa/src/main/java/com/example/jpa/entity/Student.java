package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Table(name = "studenttbl")
@Entity // => DB TABLE 과 동일해짐
public class Student {

    @Id
    @SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")
    // @GeneratedValue => create sequence studenttbl_seq start with 1 increment by
    // 50
    private Long id; // => id number(19,0) not null / primary key (id)

    // @Column(name = "sname", length = 100, nullable = false, unique = true)
    // @Column(name = "sname", columnDefinition = "varchar2(20) not null unique")
    @Column(length = 20, nullable = false)
    private String name; // => name varchar2(255 char) / null 허용

    // @Column(columnDefinition = "number(8, 0)")
    // private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Grade grade;

    @Column(columnDefinition = "varchar2(1) CONSTRAINT chk_gender CHECK (gender IN ('M', 'F'))")
    private String gender;

    // 날짜 어노테이션
    @CreationTimestamp
    private LocalDateTime cDateTime; // C_DATE_TIME

    @UpdateTimestamp
    private LocalDateTime uDateTime; // U_DATE_TIME

    @CreatedDate
    private LocalDateTime cDateTime2;
    @LastModifiedDate
    private LocalDateTime uDateTime2;

    // enum 정의
    // enum : 상수의 집합
    public enum Grade {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    }

}
