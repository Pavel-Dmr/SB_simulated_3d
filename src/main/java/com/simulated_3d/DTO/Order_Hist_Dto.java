package com.simulated_3d.DTO;

import com.simulated_3d.Constant.Order_Status;
import com.simulated_3d.Entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/* 주문 하나의 기록을 가지는 DTO*/
@Getter
@Setter
public class Order_Hist_Dto {

    private Long id;
    private String date;
    private Order_Status status;

    private List<Order_Item_Dto> order_item_dtos = new ArrayList<>();

    /* 생성자 */
    public Order_Hist_Dto(Order order)
    {
        this.id = order.getId();
        this.date = order.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.status = order.getStatus();
    }
    /* 메소드 */
    public void Add_Order_Item_Dto(Order_Item_Dto order_item_dto)
    {
        order_item_dtos.add(order_item_dto);
    }

}
