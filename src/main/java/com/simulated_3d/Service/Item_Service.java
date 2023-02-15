package com.simulated_3d.Service;

import com.simulated_3d.DTO.Item_Dto;
import com.simulated_3d.DTO.Item_Img_Dto;
import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.DTO.Main_Item_Dto;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Entity.Product.Item_Img;
import com.simulated_3d.Repository.Item_Img_Repository;
import com.simulated_3d.Repository.Item_Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Item_Service {

    private final Item_Repository item_repository;

    private final Item_Img_Repository item_img_repository;

    private final Item_Img_Service item_img_service;

    /*
        아이템을 저장합니다
        파라미터 : item_dto , item_img_file_list
         item_dto을 item으로 변환 후 DB에 저장
         이미지 파일 리스트 for문
         item_img 생성 , 해당 item_img에 주인 item을 setter
         이미지가 첫번째면 메인이미지
         item_img을 저장,item_img_file_list.get(i) 저장
         저장된 item의 id을 반환
    */
    public Long Save_Item(Item_Dto item_dto, List<MultipartFile> item_img_file_list) throws Exception
    {

        Item item = item_dto.Create_Item();

       try
       {
           item_repository.save(item);
       }
       catch(Exception e)
       {
           log.debug("아이템 정보 저장중 오류 발생");
       }

        try
        {
            for(int i = 0; i< item_img_file_list.size(); i++)
            {
                Item_Img item_img = new Item_Img();
                item_img.setItem(item);

                if(i == 0)
                    item_img.setMain(true);
                else
                    item_img.setMain(false);

                item_img_service.Save_Item_Img(item_img,item_img_file_list.get(i));
            }
        }
        catch(Exception e)
        {
            log.debug("아이템 이미지정보 저장중 오류 발생");
            log.debug(e.getMessage());
        }

    return item.getId();





    }

    /*
        아이템을 업데이트 합니다
        파라미터 : item_dto,item_img_file_list
        item_dto의 id로 item을 찾아옴
        해당 item을 item_dto로 업데이트
        item_dto로 item_img_id_list을 찾아옴
        item_img_file_list for문
            아이템 이미지을 업데이트합니다. 순차적으로(item_img_id_list,item_img_file_list);
        추후 저장된 item의 id을 반환
    */
    public Long Update_Item(Item_Dto item_dto,List<MultipartFile> item_img_files) throws Exception
    {
        Item item = item_repository.findById(item_dto.getId()).orElseThrow(EntityNotFoundException::new);
        try
        {
            item.Update_Item(item_dto);
        }
        catch(Exception e)
        {
            log.debug("아이템 업데이트 도중 에러 발생");
            e.getMessage();
        }


       try
       {
           List<Long> item_img_ids = item_dto.getItem_img_ids();

           if(item_img_ids.size() == 0)
           {
               throw new Exception("아이템 아이디 정보가 없어요");
           }
           if(item_img_files.size() == 0)
           {
               throw new Exception("아이템 파일 정보가 없어요");
           }
           for(int i=0; i < item_img_files.size(); i++)
           {

               item_img_service.Update_Item_Img(item_img_ids.get(i),item_img_files.get(i));
           }
       }
       catch(Exception e)
       {

           log.debug("아이템 이미지 업데이트 도중 에러 발생");
           log.debug(e.getMessage());
       }

        return item.getId();
    }

    /*
         아이템의 정보을 불러옵니다. (유저가 상품을 볼수 있도록 view에 필요한 item_dto을 리턴)
         파라미터 item_id
         item_id로 item의 이미지 목록 오름차순으로 찾아오기
         item_img_dto 생성
         이미지목록 for-each문
            item_img을 item_img_dto로 변환
            item_img_dto_list에 추가
        item_id로 item 찾아오기
        item을 item_dto로 변환
        item_dto에 img_dto 리스트을 setter
        return item_dto;
    */
    @Transactional(readOnly = true)
    public Item_Dto Get_Item(Long item_id)
    {
        List<Item_Img> item_imgs = item_img_repository.findByItem_IdOrderByIdAsc(item_id);

        List<Item_Img_Dto> item_img_dtos = new ArrayList<>();
        List<Long> item_img_ids = new ArrayList<>();
        for(Item_Img item_img : item_imgs)
        {
            Item_Img_Dto item_img_dto = Item_Img_Dto.of(item_img);
            item_img_dtos.add(item_img_dto);
            item_img_ids.add(item_img.getId());
        }

        Item item = item_repository.findById(item_id).orElseThrow(EntityNotFoundException::new);
        Item_Dto item_dto = Item_Dto.of(item);

        item_dto.setItem_img_dtos(item_img_dtos);
        item_dto.setItem_img_ids(item_img_ids);



        return item_dto;
    }

//    리포지토리 메서드 라인 ========================
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(Item_Search_Dto item_search_dto, Pageable pageable)
    {
        return item_repository.getAdminItemPage(item_search_dto,pageable);
    }

    @Transactional(readOnly = true)
    public Page<Main_Item_Dto> getMainItemPage(Item_Search_Dto item_search_dto, Pageable pageable)
    {
        return item_repository.getMainItemPage(item_search_dto,pageable);
    }





}
