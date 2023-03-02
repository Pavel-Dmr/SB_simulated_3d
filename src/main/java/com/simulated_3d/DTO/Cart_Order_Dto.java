package com.simulated_3d.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cart_Order_Dto {

    private Long cart_item_id;

    private List<Cart_Order_Dto> cart_order_dto_list;

}
