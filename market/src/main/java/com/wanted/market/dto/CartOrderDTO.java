package com.wanted.market.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartOrderDTO {

  private Long cartItemId;

  // 장바구니에서 여러개의 상품을 주문하므로 CartOrderDTO를 List로 저장
  private List<CartOrderDTO> cartOrderDTOList;
}
