package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Order_Item_Repository extends JpaRepository<Order_Item,Long > {

}
