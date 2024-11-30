package com.wanted.market.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainItemDTO {

  private Long id;

  private String itemNm;

  private String itemDetail;

  private String imgUrl;

  private Integer price;

  // 생성자는 반드시 public으로 줘야함.
  // @QueryProjection : 사용시 생성자는 QueryDsl에 의존
  // QueryDsl로 결과를 조회하면 MainItemDTO객체로 바로 받아옴.
  // 쿼리디에스엘 쓰기위에 큐도메인 만들어야함 (컴파일해야함)
  @QueryProjection
  public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl, Integer price) {
    this.id = id;
    this.itemNm = itemNm;
    this.itemDetail = itemDetail;
    this.imgUrl = imgUrl;
    this.price = price;
  }



}

