package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Product.Item_Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Item_Img_Repository extends JpaRepository<Item_Img,Long> {

    // 이미지의 id을 오름차순으로 정렬해서 리턴, 맨앞 이미지의 id는 대표 이미지의 id임
    List<Item_Img> findByItem_IdOrderByIdAsc(Long item_id);

    // 대표 이미지을 리턴 하는 쿼리 메서드
    Item_Img findByItem_IdAndMain(Long item_id, Boolean main_img);
}
