package com.simulated_3d.Entity;

import com.simulated_3d.Entity.Product.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Order_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    private int count;

//    TODO 연관관계 필드
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

//      TODO 연관관계 메서드
    public Order_Item Create_Order_Item(Item item,int price,int count)
    {
        Order_Item order_item = new Order_Item();
        return null;
    }
}
