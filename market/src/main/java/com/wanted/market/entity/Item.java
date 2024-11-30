package com.wanted.market.entity;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Setter
@Getter
@ToString
public class Item extends BaseEntity{
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       //상품코드

    @Column(nullable = false, length = 50)  //varchar(50) not null
    private String itemNm;  //상품명

    @Column(nullable = false)
    private int price;  //상품가격

    @Column(nullable = false)
    private int stockNumber;    //재고수량

    @Lob    //타입의 크기가 클때?
    @Column(nullable = false)
    private String itemDetail;  //상품 상세설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;  //상품 판매 상태

    public void updateItem(ItemDTO itemDTO){
        this.itemNm = itemDTO.getItemNm();
        this.price = itemDTO.getPrice();
        this.stockNumber = itemDTO.getStockNumber();
        this.itemDetail = itemDTO.getItemDetail();
        this.itemSellStatus = itemDTO.getItemSellStatus();
    }

    // 1. 변경감지 (재고수량이 바뀌니까, 재고수량이 0일 때 판매상태도 변경되니까)
    // 2. 주문 수량이 재고 수량을 넘지 않도록
    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량 : " + this.stockNumber + ")");
        }

        this.stockNumber = restStock;
        this.setItemSellStatus(this.stockNumber == 0 ? ItemSellStatus.SOLD_OUT : ItemSellStatus.SELL);
    }

    // 주문 취소시 다시 재고수량 더해주기
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

    //추상클래스로 상속함
//    @CreationTimestamp
//    private LocalDateTime regTime;  //등록시간  //요즘엔 모든 엔터티에 등록시간과 수정시간을 남김 로그남기듯이
//
//    private LocalDateTime updateTime;   //수정시간

}
