package com.example.relation.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.sports.SportsMember;
import com.example.relation.repository.sports.LockerRepository;
import com.example.relation.repository.sports.SportsMemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private LockerRepository lockerRepository;

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    // 단방향(SportsMember => Locker)

    @Test
    public void insertTest() {

        // locker 생성
        IntStream.range(1, 6).forEach(i -> {
            Locker locker = Locker.builder().name("Locker" + i).build();
            lockerRepository.save(locker);
        });

        LongStream.range(1, 6).forEach(i -> {
            SportsMember member = SportsMember.builder()
                    .locker(Locker.builder().id(i).build())
                    .name("member" + i).build();
            sportsMemberRepository.save(member);
        });

    }

    // 개별조회
    @Test
    public void readTest() {

        System.out.println(lockerRepository.findById(1L).get());
        System.out.println(sportsMemberRepository.findById(1L).get());

    }

    @Transactional
    @Test
    public void readTest2() {
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();

        System.out.println(sportsMember);
        System.out.println(sportsMember.getLocker());
    }

    @Test
    public void updateTest() {
        // 3번 회원 홍길동으로 이름 변경
        SportsMember sportsMember = sportsMemberRepository.findById(3L).get();
        sportsMember.setName("홍길동");
        sportsMemberRepository.save(sportsMember);
    }

    @Test
    public void deleteTest() {
        // 5번 회원 삭제
        sportsMemberRepository.deleteById(5L);
    }

    @Test
    public void deleteTest2() {
        // 4번 locker 삭제
        // lockerRepository.deleteById(4L);

        // 4번 회원에서 5번 locker 할당
        SportsMember member = sportsMemberRepository.findById(4L).get();
        Locker locker = lockerRepository.findById(5L).get();
        member.setLocker(locker);
        sportsMemberRepository.save(member);
        // 4번 locker 제거
        lockerRepository.deleteById(4L);
    }

    // ---------------------------
    // locker => sportsMember 접근
    // ---------------------------

    @Test
    public void readTest3() {
        Locker locker = lockerRepository.findById(1L).get();

        System.out.println(locker);
        System.out.println(locker.getSportsMember());

    }

}
