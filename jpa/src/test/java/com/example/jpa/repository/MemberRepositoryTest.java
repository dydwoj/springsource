package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.QMember;
import com.example.jpa.entity.Member.RoleType;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .name("홍길동" + i)
                    .roleType(RoleType.USER)
                    .age(i * 5)
                    .description("user " + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void queryDslTest() {

        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();

        // where name = '홍길동3'
        memberRepository.findAll(member.name.eq("홍길동")).forEach(i -> System.out.println(i));

        BooleanBuilder builder1 = builder.and(member.name.eq("홍길동3"));
        memberRepository.findAll(builder1).forEach(i -> System.out.println(i));

        // where age > 15
        memberRepository.findAll(member.age.gt(15)).forEach(i -> System.out.println(i));

        BooleanBuilder builder2 = builder.and(member.age.gt(15));
        memberRepository.findAll(builder2).forEach(i -> System.out.println(i));

        // where roleType = USER
        memberRepository.findAll(member.roleType.eq(RoleType.USER)).forEach(i -> System.out.println(i));

        BooleanBuilder builder3 = builder.and(member.roleType.eq(RoleType.USER));
        memberRepository.findAll(builder3).forEach(i -> System.out.println(i));

        // where name like '%길동%'
        memberRepository.findAll(member.name.contains("길동")).forEach(i -> System.out.println(i));

        BooleanBuilder builder4 = builder.and(member.name.contains("길동"));
        memberRepository.findAll(builder4).forEach(i -> System.out.println(i));

        // 전체 조회 후 no 의 내림차순으로 정렬하여 출력
        memberRepository.findAll(Sort.by("no").descending()).forEach(i -> System.out.println(i));

    }

}
