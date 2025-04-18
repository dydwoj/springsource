package com.example.jpa.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원은 단 하나의 팀에 소속된다 (여기가 N)

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class TeamMember {
    // id, name(회원명)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userName;

    @ManyToOne // => 테이블명 + 컬럼명으로 이름 생성
    private Team team;
}
