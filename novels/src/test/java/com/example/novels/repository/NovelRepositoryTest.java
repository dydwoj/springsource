package com.example.novels.repository;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.novels.entity.Genre;
import com.example.novels.entity.Grade;
import com.example.novels.entity.Member;
import com.example.novels.entity.Novel;

@SpringBootTest
public class NovelRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    // @Autowired
    // private GenreRepository genreRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private NovelRepository novelRepository;

    // user 50 삽입
    @Test
    public void MemberInsertTest() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .pw("1111")
                    .nickname("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    // grade 200 삽입
    @Test
    public void GradeInsertTest() {
        IntStream.rangeClosed(1, 15000).forEach(i -> {
            // novel id
            long nid = (long) (Math.random() * 5120) + 1;
            // 평점
            Long rating = (long) (Math.random() * 5) + 1;
            // user id
            int uid = (int) (Math.random() * 200) + 1;
            Grade grade = Grade.builder()
                    .rating(rating)
                    .novel(Novel.builder().id(nid).build())
                    .member(Member.builder().email("user" + uid + "@gmail.com").build())
                    .build();
            gradeRepository.save(grade);
        });
    }

    @Test
    public void getNovelTest() {
        Object[] result = novelRepository.getNovelByID(10L);
        // System.out.println(Arrays.toString(result));
        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double gradeAvg = (Double) result[2];
        System.out.println(novel);
        System.out.println(genre);
        System.out.println(gradeAvg);
    }

    @Test
    public void getNovelListTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(pageable, 3L, "The Hobbit");
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

}
