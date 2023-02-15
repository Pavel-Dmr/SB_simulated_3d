package com.simulated_3d.Controller;

import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Service.Item_Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
public class TEST_Item_Controller {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Item_Service item_service;

    @Test
    @DisplayName("상품 리스트 테스트")
    public void Item_List()
    {
        Item_Search_Dto item_search_dto = null;

        if(item_search_dto == null)
        {
            item_search_dto = new Item_Search_Dto("all", Sell_Status.SELL,"name","테스트");
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> items = item_service.getAdminItemPage(item_search_dto, pageable);

    }
}
