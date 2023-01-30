package com.simulated_3d.DTO;

import com.simulated_3d.Entity.Product.Item_Img;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class Item_Img_Dto {

    private Long id;

    private String name;

    private String ori_name;

    private String url;

    private boolean main;

    private static ModelMapper model_mapper = new ModelMapper();

    public static Item_Img_Dto of(Item_Img item_img)
    {
        return model_mapper.map(item_img,Item_Img_Dto.class);
    }
}
