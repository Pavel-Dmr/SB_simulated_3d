package com.simulated_3d.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Cart_Item_Dto {

    @NotNull(message = " 상품 아이디는 필수 입력 값 입니다.")
    private Long item_id;

    @Min(value = 1 , message = "최소 1개 이상 담아주세요.")
    private int count;
}
