package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    private MemoRepository memoRepository;

    // test 메서드 작성
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Memo memo = Memo.builder().memoText("memoText" + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void updateTest() {

        // Memo memo = Memo.builder().mno(1L).memoText("memoText update").build();
        Memo memo = memoRepository.findById(1L).get();
        memo.changeMemoText("memoText update");
        memoRepository.save(memo);
    }

    @Test
    public void readTest() {

        Memo memo = memoRepository.findById(1L).get();
        System.out.println(memo);

    }

    @Test
    public void listTest() {

        memoRepository.findAll().forEach(memo -> System.out.println(memo));
        // => 리턴값 : List<Memo>
    }

    @Test
    public void deleteTest() {
        memoRepository.deleteById(10L);
    }

    @Test
    public void queryTest1() {
        memoRepository.findByMnoLessThan(5L).forEach(i -> System.out.println(i));
    }

    @Test
    public void queryTest2() {
        memoRepository.findByMnoLessThanOrderByMnoDesc(5L).forEach(i -> System.out.println(i));
    }

    @Test
    public void queryTest3() {
        memoRepository.findByMemoTextContaining("%memo%").forEach(i -> System.out.println(i));
    }

}
