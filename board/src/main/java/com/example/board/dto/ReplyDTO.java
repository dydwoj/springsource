package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class ReplyDTO {

    private Long rno;
    private String text;
    private String replyer;
    private Long bno;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
