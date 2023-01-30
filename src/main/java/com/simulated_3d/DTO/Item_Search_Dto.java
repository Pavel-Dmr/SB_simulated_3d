package com.simulated_3d.DTO;

import com.simulated_3d.Constant.Sell_Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item_Search_Dto {

    /*
        @상품등록일 기준으로 검색
        all : 상품등록 전체
        1d : 최근 하루동안 등록된 상품
        1w : 최근 일주일동안 등록된 상품
        1m : 최근 한 달 동안 등록된 상품
        6m : 최근 6개월 동안 등록된 상품
    */
    private String date_type;

    /*
        @상품 판매 상태 기준으로 조회
        SELL : 판매중
        SOLD_OUT : 품절
    */
    private Sell_Status sell_status;

    /*
        @상품을 조회할때 어떤 유형으로 조회할지
        name : 상품명
        create_by : 상품 등록자 id
    */
    private String search_by;

    /*
        @검색어을 저장할 필드
        search_by의 유형에 따라 검색 유형이 달라집니다
        search_by = name -> 상품명을 검색
        search_by = create_by -> 상품 등록자의 id을 검색
    */
    private String search_query = "";
}
