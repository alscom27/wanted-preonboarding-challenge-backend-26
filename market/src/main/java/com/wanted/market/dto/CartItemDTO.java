package com.wanted.market.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CartItemDTO {

  @NotNull(message = "상품이 없습니다.")
  private Long itemId;

  @Min(value = 1, message = "최소 1개 이상 담아주세요.")
  private int count;

}
