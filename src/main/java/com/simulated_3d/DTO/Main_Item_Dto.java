package com.simulated_3d.DTO;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import com.querydsl.core.types.ConstructorExpression;
@Getter
@Setter
public class Main_Item_Dto {

    private Long id;
    private String name;
    private String detail;
    private String url;
    private Integer price;


    @QueryProjection
    public Main_Item_Dto(Long id, String name, String detail, String url,Integer price)
    {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.url = url;
        this.price = price;
    }
}
