package com.wanted.market.service;

import com.keduit.shop.dto.OrderDTO;
import com.keduit.shop.dto.OrderHistDTO;
import com.keduit.shop.dto.OrderItemDTO;
import com.keduit.shop.entity.*;
import com.keduit.shop.repository.ItemImgRepository;
import com.keduit.shop.repository.ItemRepository;
import com.keduit.shop.repository.MemberRepository;
import com.keduit.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ItemImgRepository itemImgRepository;

  public Long order(OrderDTO orderDTO, String email){
    Item item = itemRepository.findById(orderDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
    Member member = memberRepository.findByEmail(email);

    List<OrderItem> orderItemList = new ArrayList<>();
    OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
    orderItemList.add(orderItem);

    Order order = Order.createOrder(member, orderItemList);
    orderRepository.save(order);

    return order.getId();
  }

  public Page<OrderHistDTO> getOrderList(String email, Pageable pageable){
    List<Order> orders = orderRepository.findOrders(email, pageable);
    Long totalCount = orderRepository.countOrder(email);

    List<OrderHistDTO> orderHistDTOS = new ArrayList<>();

    for(Order order : orders){
      OrderHistDTO orderHistDTO = new OrderHistDTO(order);
      List<OrderItem> orderItems = order.getOrderItems();

      for(OrderItem orderItem : orderItems){
        ItemImg itemImg = itemImgRepository.findByItemIdAndRepImgYn(orderItem.getItem().getId(), "Y");
        OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem, itemImg.getImgUrl());
        orderHistDTO.addOrderItemDTO(orderItemDTO);
      }
      orderHistDTOS.add(orderHistDTO);
    }
    return new PageImpl<OrderHistDTO>(orderHistDTOS, pageable, totalCount);
  }

  // 로그인해야 보이더라도 체크하는 부분이 필요함
  // 현재 로그인한 사용자가 맞는지 검사 먼저하고(체크하는 로직 추가) 후 캔슬
  @Transactional(readOnly = true)
  public boolean validateOrder(Long orderId, String email){
    Member curMember = memberRepository.findByEmail(email);
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    Member savedMember = order.getMember();

    if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
      return false;
    }
    return true;
  }

  public void cancelOrder(Long orderId){
    Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
    order.cancelOrder();
  }

  public Long orders(List<OrderDTO> orderDTOList, String email){
    Member Member = memberRepository.findByEmail(email);
    List<OrderItem> orderItemList = new ArrayList<>();

    for(OrderDTO orderDTO : orderDTOList){
      Item item = itemRepository.findById(orderDTO.getItemId()).orElseThrow(EntityNotFoundException::new);

      OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());
      orderItemList.add(orderItem);
    }
    Order order = Order.createOrder(memberRepository.findByEmail(email), orderItemList);
    orderRepository.save(order);

    return order.getId();
  }


}
