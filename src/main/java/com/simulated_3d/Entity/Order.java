package com.simulated_3d.Entity;

import com.simulated_3d.Constant.Order_Status;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Setter
public class Order extends Base  {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDateTime date; //주문일

    @Enumerated(EnumType.STRING)
    private Order_Status status;



//    ============================= 연관관계 필드

    // 케스케이드타입.올 = 영속선 전이 , 고아객체 삭제 - 주문 취소되면 주문 아이템 정보도 삭제
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            ,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order_Item> order_items = new ArrayList<>();



//    ============================= 메서드

    //    주문 아이템을 추가
    public void Add_Order_Item(Order_Item order_item) {
        order_items.add(order_item);
        order_item.setOrder(this);
    }

    // 주문 생성 - 주문자 설정,
    public static Order Create_Order(Member member, List<Order_Item> order_item_list) {
        Order order = new Order();
        order.setMember(member);

        for(Order_Item order_item : order_item_list) {
            order.Add_Order_Item(order_item);
        }


        order.setStatus(Order_Status.Ready);
        order.setDate(LocalDateTime.now());
        return order;
    }

    // 총 주문 가격 리턴
    public int Get_Total_Price() {
        int total_price = 0;
        for(Order_Item order_item : order_items){
            total_price += order_item.Get_Total_Price();
        }
        return total_price;
    }

    /*
        @주문을 취소합니다
        이것의 상태을 Cancle상태로 변경
        for each - order_items
            order_item 취소
    */
    public void Cancel() {
        this.status = Order_Status.Cancle;
        for (Order_Item order_item : order_items) {
            order_item.Cancel();
        }

    }
}