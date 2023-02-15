package com.simulated_3d.Entity.Product;

import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.DTO.Item_Dto;
import com.simulated_3d.Entity.Base;
import com.simulated_3d.Exception.OutOfStockException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table
@Getter
@Setter
public class Item extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false )
    private int stock_number;

    @Lob
    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    private Sell_Status sell_status;

//    메서드 라인 ====================================================

    public void Update_Item(Item_Dto item_dto)
    {
        this.name = item_dto.getName();
        this.price = item_dto.getPrice();
        this.stock_number = item_dto.getStock_number();
        this.detail = item_dto.getDetail();
        this.sell_status = item_dto.getSell_status();
    }

    public void Remove_Stock(int count) {
        int remain_stock = this.stock_number - count;

        if(remain_stock < 0)
        {
            throw new OutOfStockException("현재 재고가 부족합니다. 현재 남은 재고 수량 :"+ this.stock_number +"개");
        }

        this.stock_number = remain_stock;

    }

    public void Add_Stock(int count) {

        this.stock_number += count;
    }
}
