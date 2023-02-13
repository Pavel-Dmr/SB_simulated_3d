package com.simulated_3d.Repository;

import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.DTO.Main_Item_Dto;
import com.simulated_3d.Entity.Product.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Item_Repository_Custom {

    Page<Item> getAdminItemPageBy(Item_Search_Dto item_search_dto, Pageable pageable);

    Page<Main_Item_Dto> getMainItemPageBy(Item_Search_Dto item_search_dto, Pageable pageable);
}
