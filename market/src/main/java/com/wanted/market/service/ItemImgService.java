package com.wanted.market.service;

import com.keduit.shop.entity.ItemImg;
import com.keduit.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional  // join 걸려있을때 write하거나 하면 트랜잭셔널이 필요해짐
public class ItemImgService {

  private final ItemImgRepository itemImgRepository;

  private final FileService fileService;

  // @Value : application.propertice에 등록한 변수의 값을 가져옴.
  @Value("${itemImgLocation}")
  private String itemImgLocation;

  public  void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

    String originalFileName = itemImgFile.getOriginalFilename();
    String imgName = "";
    String imgUrl = "";

    // 파일 업로드
    //StringUtils : String 보다 더 강력한 애들이 있음?
    if (!StringUtils.isEmpty(originalFileName)){
      imgName = fileService.uploadFile(itemImgLocation, originalFileName, itemImgFile.getBytes());
      imgUrl = "/images/item/" + imgName;
    }

    // 상품이미지 정보 저장
    itemImg.updateItemImg(originalFileName, imgName, imgUrl);
    itemImgRepository.save(itemImg);

  }

  public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
  if (!itemImgFile.isEmpty()){
    ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

    // 기존 이미지 삭제
    if(!StringUtils.isEmpty((savedItemImg.getImgName()))){
      fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
    }

    String oriImgName = itemImgFile.getOriginalFilename();
    String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
    String imgUrl = "/images/item" + imgName;
    savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

    }

  }
}
