package com.example.security;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.entity.ClubMember;
import com.example.security.entity.ClubMemberRole;
import com.example.security.repository.ClubMemberRepository;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    public void testEncoder() {
        // 원 비밀번호 : rawpassword
        String password = "1111";
        // passwordEncoder.encode(원 비밀번호) : 암호화
        String encoderPassword = passwordEncoder.encode(password);
        // password : 1111 , encoderPassword :
        // {bcrypt}$2a$10$SqcOF9eYguAvt7TzgFdE7eF0NAiH6QUQMMhC6OJjKkOIV735Fcu1a
        System.out.println("password : " + password + " , " + "encoderPassword : " + encoderPassword);

        System.out.println("비밀번호 오류 : " + passwordEncoder.matches("2222", encoderPassword));
        System.out.println(passwordEncoder.matches("1111", encoderPassword));
    }

    @Test
    public void insertTest() {
        // 모든 회원은 USER 권한 부여
        // 9, 10 회원은 MANAGER, ADMIN 권한 부여
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("user" + i)
                    .fromSocial(false)
                    .build();
            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if (i > 9) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);
        });
    }

    // @Transactional
    @Test
    public void readTest() {
        ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial("user1@gmail.com", false);
        System.out.println(clubMember);
    }
}
