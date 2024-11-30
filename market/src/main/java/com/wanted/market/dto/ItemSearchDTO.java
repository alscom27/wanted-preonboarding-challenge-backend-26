package com.wanted.market.dto;

import com.keduit.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSearchDTO {

  private String searchDateType;
  private ItemSellStatus searchSellStatus;
  private String searchBy;
  private String searchQuery = "";

}
