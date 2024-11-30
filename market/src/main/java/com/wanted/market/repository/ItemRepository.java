package com.wanted.market.repository;

import com.keduit.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item>,
    ItemRepositoryCustom{

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where replace(i.itemDetail,' ','') like %:itemDetail% order by i.price desc")
    //from 부분 보면 Item 즉 엔터티를 읽음
    //쿼리에서 replace(item_detail,' ','') like concat('%', ?, '%')  이렇게나옴
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

//    i.item_detail
    //and replace(item_detail, " ", "")
    @Query(value = "select * from item i where i.item_detail like " +
            "%:itemDetail% order by i.price desc", nativeQuery = true)
    //native 쿼리 사용
    //from 부분 보면 item 테이블을 읽음
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);

}
