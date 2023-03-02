package com.simulated_3d.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart_Detail_Dto {

    private Long cart_item_id;

    private String item_name;

    private int price;

    private int count;

    private String img_url;

    public Cart_Detail_Dto(Long cart_item_id,String item_name,int price,int count,String img_url)
    {
        this.cart_item_id = cart_item_id;
        this.item_name = item_name;
        this.price = price;
        this.count = count;
        this.img_url = img_url;
    }
}
