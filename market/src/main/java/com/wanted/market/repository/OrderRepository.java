package com.wanted.market.repository;

import com.keduit.shop.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  // 엔티티에서 읽음 (Order라고 들어감)
  @Query("select o from Order o " +
          "where o.member.email = :email " +
          "order by o.orderDate desc")
  List<Order> findOrders(@Param("email") String email, Pageable pageable);

  // 페이지 있으니까 카운트 있어야함(total이 있어야하니까)
  @Query("select count(o) from Order o " +
          "where o.member.email = :email")
  Long countOrder(@Param("email") String email);

}
