package com.wanted.market.dto;

import com.keduit.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class ItemImgDTO {

  private Long id;

  private String imgName;

  private String oriImgName;

  private String ImgUrl;

  private String repImgYn;

  private static ModelMapper modelMapper = new ModelMapper();

  // 보통 이 메서드의 이름은 의미적으로 of라고 지음
  // from, to   : itemImg를 dto로 매핑 -> dto를 리턴
  // 뒤에 들어가는(매팽하는) 객체는 클래스까지 붙여주는게 문법
  public static ItemImgDTO of(ItemImg itemImg){
    return modelMapper.map(itemImg, ItemImgDTO.class);
  }

}
