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
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime order_date;

    @Enumerated(EnumType.STRING)
    private Order_Status status;

//     TODO 연관관계 객체

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<Order_Item> order_item_list= new ArrayList<>();

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "delivery_id")
//    private Delivery delivery;

//      TODO 연관관계 메서드

public void Set_Member(Member member)
{
    this.member = member;
    this.member.getOrder_list().add(this);
}

public void Add_Order_Item(Order_Item order_item)
{
    order_item_list.add(order_item);
    order_item.setOrder(this);
}




}
