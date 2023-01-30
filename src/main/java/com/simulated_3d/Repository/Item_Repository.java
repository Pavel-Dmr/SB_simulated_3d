package com.simulated_3d.Repository;

import com.simulated_3d.DTO.Order_Dto;
import com.simulated_3d.Entity.Product.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Item_Repository extends JpaRepository<Item,Long> , Item_Custom_Repository {


}
