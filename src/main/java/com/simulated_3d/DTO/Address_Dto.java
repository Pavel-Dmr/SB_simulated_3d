package com.simulated_3d.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address_Dto {

    private String city; // 서울시
    private String district; // 송파구
    private String street; // 도로명 주소 = 송파대로 8길 15
    private String detail; //  상세 주소 = 파인타운 아파트 904동 305호

    private String zipcode; // 우편번호 05815
}
