package com.simulated_3d.Entity;

import com.simulated_3d.Entity.Product.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Order_Item extends Base{

    @Id
    @GeneratedValue
    private Long id;

    private int price;

    private int count;




//  ================================== 연관관계 필드 지연로딩 방식으로

    //성능상의 문제때문에 지연로딩 방식(조회시 해당 엔티티만 하도록)
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;



//    =============================== 메서드

    public static Order_Item Create_Order_Item(Item item, int count){
        Order_Item order_item = new Order_Item();
        order_item.setItem(item);
        order_item.setCount(count);
        order_item.setPrice(item.getPrice());
        item.Remove_Stock(count);
        return order_item;
    }

    public int Get_Total_Price(){
        return price*count;
    }//주문가격과 주문수량을 곱해서 해당 상품을 주문한 총가격을 계산하는 메소드

    public void Cancel() {
        this.getItem().Add_Stock(count);
    }
    //주문 취소시 주문 수량 만큼 상품의 재고를 더해줍니다.
}
