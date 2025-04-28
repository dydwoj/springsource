package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.QItem;
import com.example.jpa.entity.Item.SellStatus;
import com.querydsl.core.BooleanBuilder;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Item item = Item.builder()
                    .itemNm("item" + i)
                    .price(i * 2000)
                    .stockNumber(i + 10)
                    .itemDetail("Item Detail" + i)
                    .itemSellStatus(SellStatus.SELL)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void aggreateTest() {
        List<Object[]> result = itemRepository.aggreate();
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects)); // => [2550000, 51000.0, 100000, 2000, 50]
            System.out.println("아이템 합계 : " + objects[0]);
            System.out.println("아이템 평균 : " + objects[1]);
            System.out.println("아이템 최대값 : " + objects[2]);
            System.out.println("아이템 최소값 : " + objects[3]);
            System.out.println("아이템 개수 : " + objects[4]);
        }
    }

    @Test
    public void queryDslTest() {

        QItem item = QItem.item;
        // where itemNm = 'item2'
        itemRepository.findAll(item.itemNm.eq("item2"))
                .forEach(i -> System.out.println(i));
        System.out.println("==========================================================");

        // where itemNm like 'item2%'
        itemRepository.findAll(item.itemNm.startsWith("item2"))
                .forEach(i -> System.out.println(i));
        System.out.println("==========================================================");

        // where itemNm like '%item2'
        itemRepository.findAll(item.itemNm.endsWith("item2"))
                .forEach(i -> System.out.println(i));
        System.out.println("==========================================================");

        // where itemNm like '%item2%'
        itemRepository.findAll(item.itemNm.contains("item2"))
                .forEach(i -> System.out.println(i));
        System.out.println("==========================================================");

        // where itemNm = 'item2' and price > 1000
        itemRepository.findAll(item.itemNm.contains("item2").and(item.price.gt(1000)))
                .forEach(i -> System.out.println(i));
        System.out.println("==========================================================");

        // where itemNm = 'item2' and price >= 1000
        itemRepository.findAll(item.itemNm.contains("item2").and(item.price.goe(1000)))
                .forEach(i -> System.out.println(i));

        // where itemNm like '%item2%' or itemSellStaus = SOLD_OUT
        itemRepository.findAll(item.itemNm.contains("item2").or(item.itemSellStatus.eq(SellStatus.SOLD_OUT)))
                .forEach(i -> System.out.println(i));

        // where stockNumber >= 30
        itemRepository.findAll(item.stockNumber.goe(30))
                .forEach(i -> System.out.println(i));

        // where price < 35000
        itemRepository.findAll(item.price.lt(35000))
                .forEach(i -> System.out.println(i));

        // 조건 : BooleanBuilder
        BooleanBuilder builder = new BooleanBuilder();
        // where itemNm = 'item2' and price > 1000
        builder.and(item.itemNm.eq("item2"));
        builder.and(item.price.gt(1000));
        itemRepository.findAll(builder);

    }

}
