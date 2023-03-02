package com.simulated_3d.Repository;

import com.simulated_3d.DTO.Cart_Detail_Dto;
import com.simulated_3d.Entity.Cart_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Cart_Item_Repository extends JpaRepository<Cart_Item,Long> {

    Cart_Item findByCartAndItem(long cart_id, long item_id);

    @Query("select new com.shop.dto.CartDetailDto(ci.id, i.item_name, i.price, ci.count, im.img_url) " +
            "from Cart_Item ci, Item_Img im " +
            "join ci.item i " +
            "where ci.cart.id = :cart_id " +
            "and im.item.id = ci.item.id " +
            "and im.main = true " +
            "order by ci.reg_time desc"
    )
    List<Cart_Detail_Dto> findCartDetailDtoList(Long cart_id);
}
