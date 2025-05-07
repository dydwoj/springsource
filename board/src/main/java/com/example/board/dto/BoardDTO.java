package com.example.board.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class BoardDTO {

    private Long bno;

    @NotBlank(message = "insert title")
    private String title;
    @NotBlank(message = "please, insert your content")
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Member
    @Email(message = "check your email")
    @NotBlank(message = "please, insert your email")
    private String email;
    private String name;

    // reply (댓글개수)
    private Long replyCount;

}
