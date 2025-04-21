package com.example.relation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

@SpringBootTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {

        // 팀 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("team1").build());

        // 회원 정보 삽입
        teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());

    }

    @Test
    public void insertTest2() {

        // 있는 팀 정보 삽입
        Team team = teamRepository.findById(2L).get();

        // 회원 정보 삽입
        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());

    }

}
