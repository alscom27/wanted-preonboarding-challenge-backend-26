package com.wanted.market.repository;

import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//커스텀과 일반 리파지토리의 차이는 일반은 jpa리파지토리의 자식, 커스텀은 상속받지않음(그때그때 만들어서 사용)
public interface ItemRepositoryCustom {

  // 상품관리에서 페이지와 같이 목록 뿌리기
  Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);

  // 메인화면에서 페이지와 같이 목록 뿌리기
  Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable);
}
