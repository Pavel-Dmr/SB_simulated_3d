package com.simulated_3d.Entity.Product;

import com.simulated_3d.Entity.Base;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Item_Img extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ori_name;

    @Column(nullable = false)
    private String url;

    private Boolean main;


//  =================================  연관관계 필드
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

//    ================================ 메소드

    public void Update_Item_Img(String ori_name,String name,String url)
    {
        this.ori_name = ori_name;
        this.name = name;
        this.url = url;
    }

}
