package com.simulated_3d.DTO;

import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.Entity.Product.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Item_Dto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String detail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stock_number;

    private Sell_Status sell_status;

    private List<Item_Img_Dto> item_img_dto_list = new ArrayList<>();

    private List<Long> itemImgIds;

    private static ModelMapper model_mapper = new ModelMapper();

    public Item Create_Item(){
        return model_mapper.map(this, Item.class);
    }
    //ItemFormDto 내용  -> Item  엔티티 연결
    //ItemFormDto를 Item.class로 변경해주는 코드입니다. 동일한 필드명 기준으로 item 객체로 바꿔서 변환해줍니다.


    //수정할때 정보를 가져올수 있음
    public static Item_Dto of(Item item){
        return model_mapper.map(item, Item_Dto.class);
    }
    //of 메소드는 item 객체를 받아서 반대로 ItemFormDto 객체를 반환해줍니다.
    // item 엔티티  -> ItemFromDto 연결(반환, 복사)
}
