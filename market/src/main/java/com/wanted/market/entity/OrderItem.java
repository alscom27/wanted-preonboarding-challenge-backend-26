package com.wanted.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice;
  private int count;  // 각 상품별 주문수량


  public static OrderItem createOrderItem(Item item, int count){
    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setCount(count);
    orderItem.setOrderPrice(item.getPrice());

    item.removeStock(count);
    return orderItem;
  }

  public int getTotalPrice(){
    return orderPrice * count;
  }

  // 주문 취소 시 주문 수량만큼 상품의 재고를 더해줌
  public void cancel(){
    this.getItem().addStock(this.count);
  }



  //추상클래스로 상속시킴
//  private LocalDateTime regTime;
//  private LocalDateTime updateTime;
}
