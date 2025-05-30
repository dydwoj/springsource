package com.example.novels.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.novels.entity.Novel;
import com.example.novels.entity.QGenre;
import com.example.novels.entity.QGrade;
import com.example.novels.entity.QNovel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class SearchNovelRepositoryImpl extends QuerydslRepositorySupport implements SearchNovelRepository {

    public SearchNovelRepositoryImpl() {
        super(Novel.class);
    }

    @Override
    public Object[] getNovelByID(Long id) {
        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(novel.genre.eq(genre));
        query.where(novel.id.eq(id));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg().round()).from(grade)
                .where(grade.novel.eq(novel)).groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);
        Tuple result = tuple.fetchFirst();
        return result.toArray();
    }

    @Override
    public Page<Object[]> list(Pageable pageable, Long gid, String keyword) {

        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel);
        query.leftJoin(genre).on(novel.genre.eq(genre));

        JPQLQuery<Double> ratingAvg = JPAExpressions.select(grade.rating.avg().round()).from(grade)
                .where(grade.novel.eq(novel)).groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel, genre, ratingAvg);

        // ==============================================================================================================
        // 검색
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = novel.id.gt(0);
        builder.and(expression);

        // where n1_0.novel_id > 0 ? and genre_id = 3 and title like '' or author like
        // ''
        // 검색
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (gid != 0) {
            conditionBuilder.and(novel.genre.id.eq(gid));
        }

        if (!keyword.isEmpty()) {
            conditionBuilder.and(novel.title.contains(keyword));
            conditionBuilder.or(novel.author.contains(keyword));
        }

        builder.and(conditionBuilder);
        tuple.where(builder);

        // ==============================================================================================================
        // Sort 생성
        Sort sort = pageable.getSort(); // => Sort 요청한 값 가져옴

        sort.stream().forEach(order -> { // Sort 기준이 여러개 일수 있기에 forEach 로 DESC를 만듬
            // import com.querydsl.core.types.Order;
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            String prop = order.getProperty();
            PathBuilder<Novel> orderBuilder = new PathBuilder<>(Novel.class, "novel");
            // => 어떤 클래스를 기준으로 Sort 의 기준을 정의할 것인지 정의하는 구문
            // Board 클래스의 Sort 의 기준을 정의한 것

            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop))); // => 뽑아낸 SELECT 문 ORDER BY 적용
        });

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();
        long totalCnt = tuple.fetchCount();

        return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, totalCnt);

    }

}
