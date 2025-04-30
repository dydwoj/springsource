package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(Pageable pageable) {
        log.info("SearchBoard");

        // DBeaver/c##java/164번째 줄과 비교하기
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // 쿼리 조인문
        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));

        // 댓글 개수
        // 해석 : where(reply.rno(Id).eq(board(Id)))
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);

        // 쿼리들 모아주기
        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        log.info("============================");
        log.info(tuple);
        log.info("============================");

        // ============================================================================
        // Sort 생성
        // 서비스 단에서 호출한 Sort를 가지고 들어와서 Impl 단에서 Sort를 재정의
        // => PageRequest.of(0, 10, Sort.by("bno").descending());
        Sort sort = pageable.getSort(); // => bno 로 Sort 요청한 값 가져옴

        sort.stream().forEach(order -> { // Sort 기준이 여러개 일수 있기에 forEach 로 DESC를 만듬
            // import com.querydsl.core.types.Order;
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Board> orderBuilder = new PathBuilder<>(Board.class, "board");
            // => 어떤 클래스를 기준으로 Sort 의 기준을 정의할 것인지 정의하는 구문
            // Board 클래스의 Sort 의 기준을 정의한 것

            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop))); // => 뽑아낸 SELECT 문 ORDER BY 적용
        });

        // -------------------------- 전체리스트 + Sort 적용 끝남 ------------------------
        // ----------------------------------------------------------------------------

        // ----------------------------- 페이지 처리 시작 -------------------------------
        tuple.offset(pageable.getOffset()); // => 기준점에서 얼마나 떨어졌는지 자동계산

        tuple.limit(pageable.getPageSize());
        // ============================================================================
        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount(); // => 전체 개수

        // 2차원 배열 느낌 object에 각 쿼리에 해당하는 것 => 쿼리 3개가 또 배열이 된것
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Object[] getBoardByBno(Long bno) {
        log.info("SearchBoard");

        // DBeaver/c##java/164번째 줄과 비교하기
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // 쿼리 조인문
        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(board.member.eq(member));
        query.where(board.bno.eq(bno));

        // 댓글 개수
        // 해석 : where(reply.rno(Id).eq(board(Id)))
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);

        // 쿼리들 모아주기
        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        Tuple row = tuple.fetchFirst();

        return row.toArray();
    }

}
