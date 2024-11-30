package com.wanted.market.dto;

import com.keduit.shop.constant.OrderStatus;
import com.keduit.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderHistDTO {
  
  public OrderHistDTO(Order order){
    this.orderId = order.getId();
    this.orderDate = order.getOrderDate()
                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")); // String을 요구하니까 아예 여기서 패턴주고 넘김
    this.orderStatus = order.getOrderStatus();
  }
  
  private Long orderId;   //주문 번호
  
  private String orderDate;   // 주문날짜
  
  private OrderStatus orderStatus;  // 주문 상태
  
  private List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
  
  public void addOrderItemDTO(OrderItemDTO orderItemDTO){
    orderItemDTOList.add(orderItemDTO);
  }
  
}
