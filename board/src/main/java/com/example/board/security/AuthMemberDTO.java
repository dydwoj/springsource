package com.example.board.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
// 인증 받은 사용자는 User를 상속받아서 아래처럼 만들어서 사용한다
public class AuthMemberDTO extends User {

    private String email;
    private String name;
    private String password;
    private Boolean fromSocial;

    // username : id 개념
    public AuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

}
