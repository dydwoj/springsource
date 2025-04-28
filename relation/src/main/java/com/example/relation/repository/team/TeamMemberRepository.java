package com.example.relation.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    // Team 을 기준으로 멤버 찾기
    List<TeamMember> findByTeam(Team team);

    // Team 을 기준으로 멤버 찾기 => 멤버, 팀정보 조회
    @Query("SELECT m, t FROM TeamMember m Join m.team t WHERE t.id = :id")
    List<Object[]> findByMemberEqualTeam(Long id);
    // m.team t => 연결관계가 되어있음 entity를 보면 TeamMember 를 기준으로 안에 team이 ManyToOne 으로 연결됨
    // => 그래서 m(TeamMember).team 이 됨
    // * t 는 그냥 별칭
    // on 은 알아서 처리해줌 (@Id를 기준으로)

}
