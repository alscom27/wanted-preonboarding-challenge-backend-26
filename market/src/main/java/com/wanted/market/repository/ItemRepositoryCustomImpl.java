package com.wanted.market.repository;

import com.keduit.shop.constant.ItemSellStatus;
import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.dto.QMainItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.QItem;
import com.keduit.shop.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

  private JPAQueryFactory queryFactory;

  public ItemRepositoryCustomImpl(EntityManager em){
    this.queryFactory = new JPAQueryFactory(em);
  }

  private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
    return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);

  }

  // all : 전체, 1d : 최근 하루, 1w : 최근 1주일, 1m : 최근 한달, 6m : 최근 6개월
  // 기간으로 검색 (상품관리에서 사용중)
  private BooleanExpression regDtsAfter(String searchDateType){
    LocalDateTime dateTime = LocalDateTime.now();
    if(StringUtils.equals("all", searchDateType) || searchDateType == null){
      return null;
    }else if(StringUtils.equals("1d", searchDateType)){
      dateTime = dateTime.minusDays(1);
    }else if(StringUtils.equals("1w", searchDateType)){
      dateTime = dateTime.minusWeeks(1);
    }else if(StringUtils.equals("1m", searchDateType)){
      dateTime = dateTime.minusMonths(1);
    }else if(StringUtils.equals("6m", searchDateType)){
      dateTime = dateTime.minusMonths(6);
    }
    return QItem.item.regTime.after(dateTime);
  }

  // 상품명과 등록자로 검색 (상품관리에서 사용중)
  private BooleanExpression searchByLike(String searchBy, String searchQuery){
    if(StringUtils.equals("itemNm", searchBy)){
      return QItem.item.itemNm.like("%" + searchQuery + "%"); // like : %가 들어가야됨
    }else if(StringUtils.equals("createBy", searchBy)){
      return QItem.item.createBy.like("%" + searchQuery + "%");
    }
    return null;
  }
  
  // 상품명으로만 검색 (메인에서 사용)
  private BooleanExpression itemNmLike(String searchQuery){
    return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
  }

  @Override
  public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
    System.out.println("[getAdminItemPage] itemSearchDTO ===========> " + itemSearchDTO);
    System.out.println("[getAdminItemPage] pageable =================>" + pageable);

    List<Item> result = queryFactory
        .selectFrom(QItem.item)
        .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
            searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
            searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
        .orderBy(QItem.item.id.desc())
        .offset(pageable.getOffset())  // offset : 데이터를 가지고 올 시작 인덱스
        .limit(pageable.getPageSize())  // 한번에 가지고 올 최대 갯수
        .fetch();   // 맨 마지막은 페치

    Long total = queryFactory
        .select(Wildcard.count)
        .from(QItem.item)
        .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
            searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
            searchByLike(itemSearchDTO.getSearchBy(), itemSearchDTO.getSearchQuery()))
        .fetchOne();    // fetchOne() : 하나의 결과를 가져옴.
    
    return new PageImpl<>(result, pageable, total);
  }

  @Override
  public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

    // 아직 Q도메인에서 담아오는 것들을 이해못함 왜그런건지
    QItem item = QItem.item;
    QItemImg itemImg = QItemImg.itemImg;

    List<MainItemDTO> result = queryFactory
        .select(new QMainItemDTO(item.id, item.itemNm, item.itemDetail, itemImg.imgUrl, item.price))
        .from(itemImg)
        .join(itemImg.item, item)
        .where(itemImg.repImgYn.eq("Y"))  // 아이템이미지가 대표이미지여야하고
        .where(itemNmLike(itemSearchDTO.getSearchQuery()))  //아이템이름이 같아야함
        .orderBy(item.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(Wildcard.count) // wildcard가 뭐지
        .from(itemImg)
        .join(itemImg.item, item)
        .where(itemImg.repImgYn.eq("Y"))
        .where(itemNmLike(itemSearchDTO.getSearchQuery()))
        .fetchOne();

    return new PageImpl<>(result, pageable, total);
  }


}
