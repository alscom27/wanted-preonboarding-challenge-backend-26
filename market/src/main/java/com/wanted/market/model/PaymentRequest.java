package com.wanted.market.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

  private String productId;

  private int amount;

}
