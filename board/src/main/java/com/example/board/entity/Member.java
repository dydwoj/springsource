package com.example.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString

@Table(name = "Board_Member")
@Entity
public class Member extends BaseEntity {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

}
