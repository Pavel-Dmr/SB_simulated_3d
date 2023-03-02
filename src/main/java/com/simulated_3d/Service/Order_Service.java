package com.simulated_3d.Service;

import com.simulated_3d.DTO.Order_Dto;
import com.simulated_3d.DTO.Order_Hist_Dto;
import com.simulated_3d.DTO.Order_Item_Dto;
import com.simulated_3d.Entity.Order;
import com.simulated_3d.Entity.Order_Item;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Entity.Member;
import com.simulated_3d.Entity.Product.Item_Img;
import com.simulated_3d.Repository.Item_Img_Repository;
import com.simulated_3d.Repository.Item_Repository;
import com.simulated_3d.Repository.Member_Repository;
import com.simulated_3d.Repository.Order_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class Order_Service {

    private final Item_Repository item_repository;

    private final Member_Repository member_repository;

    private final Order_Repository order_repository;

    private final Item_Img_Repository item_img_repository;

//    =================================================================== 메소드

    /*
        @주문 요청합니다
        item 생성 ( order_dto )
        member 생성 (email로 찾아옴)
        order_item_list 생성
        item 정보와 order_dto의 주문 수량을 토대로 order_item 생성
        order_item_list에 add
        member와 order_item_list을 기반으로 주문을 생성
        주문의 id을 리턴
    */
    public Long Order(Order_Dto order_dto, String email)
    {
        Item item = item_repository.findById(order_dto.getId()).orElseThrow(EntityNotFoundException::new);
        Member member = member_repository.findByEmail(email);

        List<Order_Item> order_item_list = new ArrayList<>();
        Order_Item order_item = Order_Item.Create_Order_Item(item,order_dto.getCount());

        order_item_list.add(order_item);

        Order order = Order.Create_Order(member,order_item_list);

        order_repository.save(order);

        return order.getId();
    }

    /*
        @주문 요청 ( 상품이 다수 일때)
        member 생성 ( email 통해서)
        order_items 생성 ( order을 위해서)

        for each - order_dtos
            item 생성 ( order_dto.id 통해서 )
            order_item 생성 (item / order_dto.count 통해서)
            order_items.add 합니다

        order 생성 (member / order_items)
        order 저장

        success
            order.id 리턴
    */
    public Long Order_List(List<Order_Dto> order_dtos ,String email)
    {
        Member member = member_repository.findByEmail(email);
        List<Order_Item> order_items = new ArrayList<>();

        for(Order_Dto order_dto: order_dtos)
        {
            Item item = item_repository.findById(order_dto.getId()).orElseThrow();
            Order_Item order_item = Order_Item.Create_Order_Item(item,order_dto.getCount());
            order_items.add(order_item);
        }

        Order order = Order.Create_Order(member,order_items);
        order_repository.save(order);

        return order.getId();
    }
    /*
         @주문 리스트을 요청합니다
         email / pageable
         orders 생성 ( email과 pageable을 통해서 데이터을 정렬하고 원하는 크기만큼 조절할수 있습니다)
         total_count(email통해서 나의 총 주문 수을 리턴)
         order_hist_dtos 생성 ( view에 보여주기 위해서)

         for each - orders
            order_hist_dto 생성 ( order 통해서)
            order_items 생성 ( order의 상품리스트 통해서)

            for each - order_items
                item_img 생성(메인 이미지담당 객체) (order_item.getItem().getId() , true)
                order_item_dto
    */
    @Transactional(readOnly = true)
    public Page<Order_Hist_Dto> Get_Order_Page(String email, Pageable pageable)
    {
        List<Order> orders = order_repository.Find_Order_List(email,pageable);

        Long total_count = order_repository.Count_Order(email);

        List<Order_Hist_Dto> order_hist_dtos = new ArrayList<>();

        for (Order order : orders) {
            Order_Hist_Dto order_hist_dto = new Order_Hist_Dto(order);
            List<Order_Item> order_items = order.getOrder_items();

            for (Order_Item order_item : order_items) {
                Item_Img item_img = item_img_repository.findByItem_IdAndMain(order_item.getItem().getId(),true);
                Order_Item_Dto order_item_dto =    new Order_Item_Dto(order_item, item_img.getUrl());
                order_hist_dto.Add_Order_Item_Dto(order_item_dto);
            }

            order_hist_dtos.add(order_hist_dto);
        }
        return new PageImpl<Order_Hist_Dto>(order_hist_dtos, pageable, total_count);
        //페이지 구현  객체를 생성하여 반환
    }

    /*
        @ 주문에 문제는 없는지 무결성 체크합니다 / 현재 로그인한 유저와 주문한 유저가 같은지
        order_id / email
        order 생성 ( order_id 통해서 / 주문번호)
        current_member 생성 (email 통해서)
        order_member 생성 (order 통해서)

        if - 만약 현재 로그인한 유저와 주문한 유저의 이메일이 같지 않으면
            return false;
        success
            return true;
    */
    @Transactional(readOnly = true)
    public boolean Validate_Order(Long order_id,String email)
    {

        Order order = order_repository.findById(order_id).orElseThrow(EntityNotFoundException::new);
        Member current_member = member_repository.findByEmail(email);
        Member order_member = order.getMember();

        if(!StringUtils.equals(current_member.getEmail(),order_member.getEmail()))
        {
            return false;
        }
        return true;
    }

    /*
        @주문을 취소합니다
        order 생성 (order_id 통해서)
        order 캔슬 요청
    */
    public void Cancel_Order(Long order_id)
    {
        Order order = order_repository.findById(order_id).orElseThrow(EntityNotFoundException::new);
        order.Cancel();
    }




}
