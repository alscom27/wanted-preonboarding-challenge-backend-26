package com.wanted.market.entity;

import com.keduit.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Table(name = "orders")
public class Order extends BaseEntity{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  //(나(여기서는 order)는 여러개 멤버는 하나 = ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
              orphanRemoval = true, fetch = FetchType.LAZY)
  //양방향으로 조인을 검 주인의 주체를 표시함
  // all : 부모 엔티티의 영속성 상태 변화를 자식엔티티에 모두 전이
  // orphanRemoval = true : 고아 객체 제거 모드를 true로, @OneToOne, OneToMany어노테이션의 옵션으로 사용
  private List<OrderItem> orderItems = new ArrayList<>();
  
  private LocalDateTime orderDate;
  
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;  //주문 상태


  public void addOrderItem(OrderItem orderItem){
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public static Order createOrder(Member member, List<OrderItem> orderItemList){
    Order order = new Order();
    order.setMember(member);
    for(OrderItem orderItem : orderItemList){
      order.addOrderItem(orderItem);
    }
    order.setOrderStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  public int getTotalPrice(){
    int totalPrice = 0;
    for(OrderItem orderItem : orderItems){
      totalPrice += orderItem.getTotalPrice();   // 여기 getTotalPrice는 오더아이템에 있는거임
    }
    return totalPrice;
  }

  // 주문 취소 시 주문상태를 cancel로 변경
  public void cancelOrder(){
    this.orderStatus = OrderStatus.CANCEL;

    for(OrderItem orderItem : orderItems){
      orderItem.cancel();
    }
  }

  //추상클래스로 상속시킴
//  //얘네는 관리를 위해서 줌
//  private LocalDateTime regTime;
//  private LocalDateTime updateTime;
  
}
