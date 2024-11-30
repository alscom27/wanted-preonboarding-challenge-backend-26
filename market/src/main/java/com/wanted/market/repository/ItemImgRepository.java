package com.wanted.market.repository;

import com.keduit.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
  List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

  ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}