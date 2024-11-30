package com.wanted.market.service;

import com.keduit.shop.dto.ItemDTO;
import com.keduit.shop.dto.ItemImgDTO;
import com.keduit.shop.dto.ItemSearchDTO;
import com.keduit.shop.dto.MainItemDTO;
import com.keduit.shop.entity.Item;
import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.repository.ItemImgRepository;
import com.keduit.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional  // itemImgRepository에 등록할거니까 (join생각)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ItemImgRepository itemImgRepository;
  private final ItemImgService itemImgService;

  public Long saveItem(ItemDTO itemDTO, List<MultipartFile> itemImgFileList) throws Exception{

    // 상품 등록
    Item item = itemDTO.createItem(); //createItem에서 modelMapper를 이용해 만듬
    itemRepository.save(item);

    // 이미지 등록
    for (int i=0; i<itemImgFileList.size(); i++){
      ItemImg itemImg = new ItemImg();
      itemImg.setItem(item);  //itemImg의 Item의 아이디로 join걸려있음
      if(i==0){
        itemImg.setRepImgYn("Y");
      }else{
        itemImg.setRepImgYn("N");
      }
      itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
    }

    return item.getId();
  }

  // @Transactional(readOnly = true) : 상품 데이터를 읽기만 하므로 JPA에게 더티체킹(변경 감지)을 하지않도록 설정
  // -> 성능상 이점을 챙길 수 있음
  @Transactional(readOnly = true)
  public ItemDTO getItemDtl(Long itemId){
    List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
    List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
    for (ItemImg itemImg : itemImgList){
      ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg);
      itemImgDTOList.add(itemImgDTO);
    }

    Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
    ItemDTO itemDTO = ItemDTO.of(item);
    itemDTO.setItemImgDTOList(itemImgDTOList);

    return itemDTO;
  }

  // 몇 번 상품을 업데이트 했는지 알려고 Long 반환
  public Long updateItem(ItemDTO itemDTO,
                         List<MultipartFile> itemImgFileList) throws  Exception{
    // 상품 수정
    System.out.println("======updateItem, itemDTO ========> " + itemDTO);

    Item item = itemRepository.findById(itemDTO.getId()).orElseThrow(EntityNotFoundException::new);
    item.updateItem(itemDTO);

    // 이미지 등록을 위한 이미지 아이디들을 담은 리스트
    List<Long> itemImgIds = itemDTO.getItemImgIds();

    // 이미지 등록
    for(int i=0; i<itemImgFileList.size(); i++){
      System.out.println("==================");
      System.out.println(i + "*****" + itemImgIds.get(i));
      System.out.println("*********" + itemImgFileList.get(i));
      System.out.println("==================");

      itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
    }
    return item.getId();
  }

  @Transactional(readOnly = true)
  public Page<Item> getAdminPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
    return itemRepository.getAdminItemPage(itemSearchDTO, pageable);
  }

  @Transactional(readOnly = true)
  public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable){
    return itemRepository.getMainItemPage(itemSearchDTO, pageable);
  }
  
}
