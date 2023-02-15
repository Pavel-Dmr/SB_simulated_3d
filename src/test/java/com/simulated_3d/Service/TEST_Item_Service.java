package com.simulated_3d.Service;

import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Repository.Item_Img_Repository;
import com.simulated_3d.Repository.Item_Repository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
public class TEST_Item_Service {

    @Autowired
    private Item_Img_Repository item_Img_Repository;

    @Autowired
    Item_Repository item_repository;

    public Page<Item> getAdminItemPage(Item_Search_Dto item_search_dto, Pageable pageable)
    {
        return item_repository.getAdminItemPage(item_search_dto,pageable);
    }

    @Test
    @DisplayName("상품 리스트 테스트")
    public void QueryDsl_01()
    {
        Item_Search_Dto item_search_dto = null;

        if(item_search_dto == null)
        {
            item_search_dto = new Item_Search_Dto("all", Sell_Status.SELL,"name","테스트");
        }

        Pageable pageable = PageRequest.of(0, 10);

        Page<Item> item_page = item_repository.getAdminItemPage(item_search_dto,pageable);

    }
}
