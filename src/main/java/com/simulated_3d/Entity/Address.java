package com.simulated_3d.Entity;

import com.simulated_3d.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_key")
    private Member member;

    private String city; // 서울시
    private String district; // 송파구
    private String street; // 도로명 주소 = 송파대로 8길 15
    private String detail; //  상세 주소 = 파인타운 아파트 904동 305호

    private String zipcode; // 우편번호 05815




}
