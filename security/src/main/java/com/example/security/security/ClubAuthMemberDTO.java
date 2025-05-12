package com.example.security.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
// 인증 받은 사용자는 User를 상속받아서 아래처럼 만들어서 사용한다
public class ClubAuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String name;
    private String password;
    private boolean fromSocial;

    // 소셜 로그인 처리
    private Map<String, Object> attr;

    // username : id 개념
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    // 소셜 로그인 포함
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(username, password, fromSocial, authorities);
        this.attr = attr;
    }

    // 소셜 로그인 처리
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

}
