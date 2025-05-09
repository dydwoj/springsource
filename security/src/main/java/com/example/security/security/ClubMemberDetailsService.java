package com.example.security.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.entity.ClubMember;
import com.example.security.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class ClubMemberDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    // 로그인 처리 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("usernameTestRIGHTNOW : {}", username);

        ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial(username, false);

        if (clubMember == null)
            throw new UsernameNotFoundException("이메일 확인");

        // entity => dto
        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));

        clubAuthMemberDTO.setName(clubMember.getName());
        clubAuthMemberDTO.setFromSocial(clubMember.isFromSocial());

        return clubAuthMemberDTO;
    }

}
