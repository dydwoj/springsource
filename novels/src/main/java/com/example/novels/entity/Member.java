package com.example.novels.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString

@Entity(name = "NOVEL_MEMBER")
public class Member {

    @Id
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String nickname;

    private boolean social;

}
