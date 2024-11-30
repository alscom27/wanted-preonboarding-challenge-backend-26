package com.wanted.market.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log  // @Log : log4j log4j2 다 사용가능함
public class FileService {

  public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

    // Universally Unique IdentiFied : 범용 고유 식별자
    //UUID : util에 있는 기능, 중복되지 않는 유일한 값을 구성할 때 사용
    UUID uuid = UUID.randomUUID();
    String extention = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자 붙이려고?
    String savedFileName = uuid.toString() + extention;
    String fileUploadFullUrl = uploadPath + "/" + savedFileName;

    log.info("======================");
    log.info(uuid.toString());
    log.info(extention);
    log.info(savedFileName);
    log.info(fileUploadFullUrl);
    log.info("======================");

    FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
    fos.write(fileData);
    fos.close();
    return savedFileName;
  }

  public void deleteFile(String filePath) throws Exception{
    // 저장된 파일의 경로를 이용하여 파일 객체를 생성
    File deleteFile = new File(filePath);

    // 해당 파일이 있으면 삭제
    if(deleteFile.exists()){
      deleteFile.delete();
      log.info("파일을 삭제하였습니다.");
    }else{
      log.info("파일이 존재하지 않습니다.");
    }
  }

}
