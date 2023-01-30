package com.simulated_3d.Entity;

import com.simulated_3d.Entity.Product.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Cart_Item extends Base{

    @Id
    @GeneratedValue
    private long id;

    private int count;

    //    연관관계 필드
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Item item;



}
