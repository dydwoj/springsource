package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Member> members() {
        QMember member = QMember.member;
        // member.name.eq("member1");

        // select m from Member m where m.name = 'user1' order by m.name desc;
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        query.select((member));

        // fetch() : 리스트 조회, 데이터 없는 경우 빈 리스트 반환
        // 2) fetchFirst() : limit(1), fect one()
        // 3) fetchOne() : 결과가 없으면 null, 둘 이상이면 NonUniqueException
        // 4) fetchResults() : 페이징 정보 포함, total count 쿼리 추가 실행
        // 5) fetchCount() : count 쿼리로 변경해서 count 수 주회
        List<Member> list = query.fetch();
        return list;
    }

    @Override
    public List<Item> items() {
        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item);

        // itemName like '티셔츠' and price > 20000
        query.where(item.name.contains("티셔츠").and(item.price.gt(200000)));
        query.select(item);
        return query.fetch();
    }

    @Override
    public List<Object[]> joinTest() {
        // order o join member m on o.member = m.member_id
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member));// ManyToOne : order 기준
        query.join(orderItem).on(order.eq(orderItem.order)); // OneToMany : order 기준
        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem);
        List<Tuple> result = tuple.fetch();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return list;
    }

    @Override
    public List<Object[]> subQueryTest() {

        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        JPQLQuery<Order> query = from(order);
        query.join(member).on(order.member.eq(member))
                .join(orderItem).on(order.eq(orderItem.order));

        // SELECT 주문건수
        JPQLQuery<Long> orderCnt = JPAExpressions.select(orderItem.order.count())
                .from(orderItem)
                .where(orderItem.order.eq(order)).groupBy(orderItem.order);

        // SELECT 주문총금액
        JPQLQuery<Integer> orderSum = JPAExpressions.select(orderItem.orderPrice.sum())
                .from(orderItem)
                .where(orderItem.order.eq(order)).groupBy(orderItem.order);

        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem, orderCnt, orderSum);
        List<Tuple> result = tuple.fetch();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return list;
    }

}
