package com.simulated_3d.Service;

import com.simulated_3d.Entity.Product.Item_Img;
import com.simulated_3d.Repository.Item_Img_Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class Item_Img_Service {

    @Value("${item_img_location}")
    private String item_img_location;

    private final Item_Img_Repository item_img_repository;

    private final File_Service file_service;

    /*
        @ 상품 이미지을 저장합니다
        item_img / item_img_file
        item_img_file의 원래 이름을 ori_name으로 선언
        name,url 선언
        if - ori_name이 비어있지않다면
            파일을 업로드하고 UUID.확장자명으로 name을 설정
            url은 "/images/ + name
        item_img을 업데이트 (ori_name , name, url)
    */
    public void Save_Item_Img(Item_Img item_img, MultipartFile item_img_file) throws Exception
    {
        String ori_name = item_img_file.getOriginalFilename();
        String name = "";
        String url = "";

        if(!StringUtils.isEmpty(ori_name))
        {
            name = file_service.Upload_File(item_img_location,ori_name,item_img_file.getBytes());
            url = "/images/" + name;
        }

        item_img.Update_Item_Img(ori_name,name,url);
        item_img_repository.save(item_img);
    }

    /*
        상품 이미지을 업데이트
        item_img_id / item_img_file
        if - 아이템 이미지 파일이 있으면
            업데이트할려는 아이템 이미지을 찾아서 업데이트 아이템 이미지 ETT에 선언
            if - 업데이트 아이템 이미지에 이름이 있으면
                해당 항목의 이미지 파일 삭제
            아이템 이미지 파일의 본 파일명 선언
            새로운 이미지을 업로드하고 저장된 이미지명을 name 선언
            url 선언
            새롭게 업데이트된 이미지 정보을 update_item_img에 업데이트
    */
    public void Update_Item_Img(Long item_img_id, MultipartFile item_img_file) throws Exception
    {
        if(!item_img_file.isEmpty())
        {
            Item_Img update_item_img = item_img_repository.findById(item_img_id).orElseThrow(EntityNotFoundException::new);

            try
            {
                if(!StringUtils.isEmpty(update_item_img.getName()))
                {
                    file_service.Delete_File(item_img_location + "/" + update_item_img.getName());
                }
            }
            catch (Exception e)
            {
                //원래 있던 이미지 파일 삭제
                log.debug("이미지 파일 삭제중 오류");
                e.getMessage();
            }

           try
           {
               String ori_name = item_img_file.getOriginalFilename();
               String name = file_service.Upload_File(item_img_location,ori_name,item_img_file.getBytes());

               String url = "/images/" + name;

               update_item_img.Update_Item_Img(ori_name,name,url);
           }
           catch(Exception e)
           {
               log.debug("이미지 파일 업로드 중 오류");
               e.getMessage();
           }




        }
    }
}
