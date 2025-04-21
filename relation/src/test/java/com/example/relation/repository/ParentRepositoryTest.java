package com.example.relation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.relation.entity.cascade.Child;
import com.example.relation.entity.cascade.Parent;
import com.example.relation.repository.cascade.ChildRepository;
import com.example.relation.repository.cascade.ParentRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ParentRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 기존 방식
        Parent parent = new Parent();
        parent.setName("parent1");
        parentRepository.save(parent);

        Child child = new Child();
        child.setName("child1");
        child.setParent(parent);
        childRepository.save(child);

        child = new Child();
        child.setName("child2");
        child.setParent(parent);
        childRepository.save(child);
    }

    @Test
    public void insertTest2() {
        // 부모를 저장하면서 자식도 같이 저장
        Parent parent = new Parent();
        parent.setName("parent3");

        parent.getChilds().add(Child.builder().name("홍길동").parent(parent).build());
        parent.getChilds().add(Child.builder().name("성춘향").parent(parent).build());
        parent.getChilds().add(Child.builder().name("박보검").parent(parent).build());

        parentRepository.save(parent);
    }

    @Test
    public void deleteTest() {
        // 부모 삭제시, 자식도 같이 삭제
        parentRepository.deleteById(1L);
    }

    @Commit
    @Transactional
    @Test
    public void deleteTest2() {
        //
        Parent parent = parentRepository.findById(3L).get();
        // 객체 개념과는 달리, 찍히는건 제거로 찍히나 DB 에는 반영이 안됬음
        parent.getChilds().remove(0); // => 홍길동은 고아 객체 상태
        System.out.println(parent.getChilds());
        parentRepository.save(parent);
    }

}
