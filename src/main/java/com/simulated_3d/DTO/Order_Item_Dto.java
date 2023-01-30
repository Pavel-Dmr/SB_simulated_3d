package com.simulated_3d.DTO;

import com.simulated_3d.Entity.Order_Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class Order_Item_Dto {

    private String name;
    private int count;
    private int price;
    private String url;

    /* 생성자 */
    public Order_Item_Dto (Order_Item order_item,String url)
    {
        this.name = order_item.getItem().getName();
        this.count = order_item.getCount();
        this.price = order_item.getPrice();
        this.url = url;
    }
}
