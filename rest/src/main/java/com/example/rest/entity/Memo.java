package com.example.rest.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// db 기준
// 번호(mno), 내용(memo_text-200), 생성날짜(created_date), 수정날짜(updated_date)
// mno : 시퀀스 자동증가, pk
// 나머지 칼럼 : not null
@Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@EntityListeners(value = AuditingEntityListener.class)
@Entity
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public void changeMemoText(String memoText) {
        this.memoText = memoText;
    }
}
