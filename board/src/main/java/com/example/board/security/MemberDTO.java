package com.example.board.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
// 인증 받은 사용자는 User를 상속받아서 아래처럼 만들어서 사용한다
public class MemberDTO {

    @NotBlank(message = "Please, input your email")
    @Email(message = "Check your email address")
    private String email;

    @NotBlank(message = "Please, input your name")
    private String name;

    @NotBlank(message = "Check your password")
    private String password;

    private boolean fromSocial;

}
