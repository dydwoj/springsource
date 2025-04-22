package com.example.mart.repository;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
import com.example.mart.entity.constant.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemrRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Test
    public void testMemberInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .city("서울" + i)
                    .street("724-11" + i)
                    .zipcode("1650" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testItemInsert() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            Item item = Item.builder()
                    .name("item" + i)
                    .price(i * 20000)
                    .stockQuantity(i * 5)
                    .build();
            itemrRepository.save(item);
        });
    }

    // 주문하다 : Order + OrderItem insert
    @Test
    public void testOrderInsert() {
        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();
        orderRepository.save(order);

        // 주문과 관련된 상품은 OrderItem 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemrRepository.findById(2L).get())
                .order(order)
                .orderPrice(39000)
                .count(1)
                .build();
        orderItemRepository.save(orderItem);

        orderItem = OrderItem.builder()
                .item(itemrRepository.findById(3L).get())
                .order(order)
                .orderPrice(45000)
                .count(1)
                .build();
        orderItemRepository.save(orderItem);

    }

    @Transactional
    @Test
    public void testRead1() {
        // 주문 조회(주문번호)
        Order order = orderRepository.findById(1L).get();
        System.out.println(order);

        // 주문자 정보 조회
        System.out.println(order.getMember());
    }

    @Transactional
    @Test
    public void testRead2() {
        // 특정회원의 주문 내역 전체 조회
        Member member = memberRepository.findById(1L).get();
        System.out.println(member.getOrders());
    }

    @Transactional
    @Test
    public void testRead3() {
        // 주문상품의 정보 조회
        OrderItem orderItem = orderItemRepository.findById(1l).get();
        System.out.println(orderItem); // => OrderItem(id=1, orderPrice=19600, count=1)

        // 주문상품의 상품명 조회
        System.out.println(orderItem.getItem());

        // 주문상품을 주문한 고객 조회
        System.out.println(orderItem.getOrder().getMember());
    }

    @Transactional
    @Test
    public void testRead4() {
        Order order = orderRepository.findById(3L).get();
        System.out.println(order);

        // 주문을 통해 주문 아이템 조회
        order.getOrderItems().forEach(item -> System.out.println(item));
    }

    @Test
    public void testDelete1() {
        // memberRepository.deleteById(5L);

        // member id로 주문 찾아오기 ==> 메서드 생성

        // 주문 상품 취소 :
        // 주문 취소
        // 멤버 제거
        memberRepository.deleteById(1L);
    }

    @Test
    public void testDelete2() {
        // 제거 기본
        // 주문 아이템 제거
        orderItemRepository.deleteById(2L);
        // 주문 제거
        orderRepository.deleteById(2L);
    }

    @Test
    public void testDelete3() {
        // 주문 제거(주문상품 같이 제거)
        // 영속성 제거 : 부모쪽에 cascade 작성
        orderRepository.deleteById(1L);
    }

    @Commit
    @Transactional
    @Test
    public void testDelete4() {
        Order order = orderRepository.findById(4L).get();
        // 현재 주문과 연결된 주문상품 조회
        System.out.println(order.getOrderItems());

        // 첫번째 자식 제거
        order.getOrderItems().remove(0);
        // orphanRemoval = true 추가
        orderRepository.save(order);
    }

    @Test
    public void testOrderInsert2() {
        // order 저장시, orderItem 같이 저장
        // CascadeType.PERSIST 설정
        Order order = Order.builder()
                .member(Member.builder().id(1L).build())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        // 주문과 관련된 상품은 OrderItem 삽입
        OrderItem orderItem = OrderItem.builder()
                .item(itemrRepository.findById(2L).get())
                .order(order)
                .orderPrice(39000)
                .count(1)
                .build();
        // orderItemRepository.save(orderItem);
        order.getOrderItems().add(orderItem);
        orderRepository.save(order);

    }

    @Test
    public void testDeliveryInsert() {
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("부산")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        deliveryRepository.save(delivery);

        // 주문과 연결
        Order order = orderRepository.findById(4L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Transactional
    @Test
    public void testDeliveryRead() {
        // 배송조회
        System.out.println(deliveryRepository.findById(1L));
        // 배송과 관련있는 주문 조회(X) => 양방향으로 열지 않아서
        // 주문과 관련있는 배송 조회
        Order order = orderRepository.findById(4L).get();
        System.out.println(order.getDelivery().getDeliveryStatus());
    }

    @Transactional
    @Test
    public void testDeliveryRead2() {
        // 배송과 관련있는 주문 조회
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println("주문 조회 : " + delivery.getOrder());
        System.out.println("주문자 조회 : " + delivery.getOrder().getMember());
        System.out.println("주문 아이템 조회 : " + delivery.getOrder().getOrderItems());
    }

    @Test
    public void testDeliveryInsert2() {
        // 영속성 관리를 통한 부모-자식 같이 저장
        Delivery delivery = Delivery.builder()
                .zipcode("15011")
                .city("서울")
                .street("120-11")
                .deliveryStatus(DeliveryStatus.READY)
                .build();
        // deliveryRepository.save(delivery);

        // 주문과 연결
        Order order = orderRepository.findById(5L).get();
        order.setDelivery(delivery);
        orderRepository.save(order);
    }

    @Test
    public void testDelete5() {
        // order 지우면서 배송정보 제거, 주문상품 제거
        orderRepository.deleteById(5L);
    }

    @Test
    public void testCategoryInsert() {
        Category category = Category.builder().name("의류").build();
        categoryRepository.save(category);

        CategoryItem categoryItem = CategoryItem.builder()
                .category(category)
                .item(itemrRepository.findById(4L).get())
                .build();
        categoryItemRepository.save(categoryItem);
    }

}
