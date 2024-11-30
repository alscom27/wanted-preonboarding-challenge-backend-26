package com.wanted.market.dto;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemDTO {

    private Long id;

    @NotBlank(message = "상품명은 필수입력입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수입력입니다.")
    private Integer price;  //Integer는 디폴트가 null이라 null을 허용해서 가능 int는 아니라서 뷰에서부터 빅엿을 선물함

    @NotNull(message = "재고수량을 입력해주세요.")
    private Integer stockNumber;

    @NotBlank(message = "상품설명은 필수입력입니다.")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();

    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // dto -> 엔티티
    public Item createItem(){
        return modelMapper.map(this, Item.class);   //모델매퍼 사용하려면 디펜던시, 라이브러리니까
    }

    // 엔티티 -> dto
    public static ItemDTO of(Item item){
        return modelMapper.map(item, ItemDTO.class);
    }

}

