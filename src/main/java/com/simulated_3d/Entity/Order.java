package com.simulated_3d.Entity;

import com.simulated_3d.Constant.Order_Status;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order")
@Getter @Setter
public class Order  {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime date; //주문일

    @Enumerated(EnumType.STRING)
    private Order_Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            ,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order_Item> order_item_list = new ArrayList<>();


    public void Add_Order_Item(Order_Item order_item) {
        order_item_list.add(order_item);
        order_item.setOrder(this);
    }

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

    public int Get_Total_Price() { //총주문 금액을 구하는 메소드
        int total_price = 0;
        for(Order_Item order_item : order_item_list){
            total_price += order_item.Get_Total_Price();
        }
        return total_price;
    }

    public void Cancel() {
        this.status = Order_Status.Cancle;
        for (Order_Item order_item : order_item_list) {
            order_item.Cancel();
        }
        //Item 클래스에서 주문 취소시 주문 수량을 상품의 재고에 더해주는 로직과
        //주문 상태를 취소 상태로 바꿔 주는 메소드를 구현합니다.
    }
}