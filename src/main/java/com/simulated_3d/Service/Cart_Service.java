package com.simulated_3d.Service;

import com.simulated_3d.DTO.Cart_Detail_Dto;
import com.simulated_3d.DTO.Cart_Item_Dto;
import com.simulated_3d.Entity.Cart;
import com.simulated_3d.Entity.Cart_Item;
import com.simulated_3d.Entity.Member;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Repository.Cart_Item_Repository;
import com.simulated_3d.Repository.Cart_Repository;
import com.simulated_3d.Repository.Item_Repository;
import com.simulated_3d.Repository.Member_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class Cart_Service {

    private final Item_Repository item_repository;
    private final Member_Repository member_repository;
    private final Cart_Repository cart_repository;
    private final Cart_Item_Repository cart_item_repository;
    private final Order_Service order_service;


    /*
       TODO 현재 상품을 카트에 담기(장바구니 상품 정보,이메일)
       해당 상품 찾기(장바구니 상품의 id)
       주문자 찾기(이메일)
       장바구니 찾기(주문자정보)

       if(해당 주문자의 장바구니가 생성이 되지 않았다면)
           - 장바구니 생성 ( 주문자 )
           - 장바구니 저장

        현재 상품이 장바구니에 있는지 찾는다

         if(이미 장바구니에 존재한다)
            - 해당 장바구니 상품의 갯수 추가
            - 해당 장바구니 상품 id 리턴
       else(장바구니에 존재하지 않는다)
            - 장바구니 상품을 생성
            - 해당 장바구니 상품을 저장
            - 해당 장바구니 상품 id 리턴
    */
    public Long Add_Cart(Cart_Item_Dto cart_item_dto,String email)
    {
        Item item = item_repository.findById(cart_item_dto.getItem_id())
                .orElseThrow(EntityNotFoundException::new);

        Member member = member_repository.findByEmail(email);

        Cart cart = cart_repository.findByMember(member.getId());

        if(cart == null)
        {
            cart = Cart.Create_Cart(member);
            cart_repository.save(cart);
        }

        Cart_Item saved_cart_item = cart_item_repository.findByCartAndItem(cart.getId(),item.getId());

        if(saved_cart_item != null)
        {
            saved_cart_item.Add_Count(cart_item_dto.getCount());

            return saved_cart_item.getId();
        }
        else
        {
            Cart_Item cart_item = Cart_Item.Create_Cart_Item(cart,item,cart_item_dto.getCount());
            cart_item_repository.save(cart_item);

            return cart_item.getId();
        }
    }

    /*
         TODO 현재 로그인한 회원의 장바구니 리스트을 조회(이메일)
         장바구니 상세 정보 리스트 생성
         주문자 찾아오기(이메일)
         장바구니 찾아오기(주문자id)

         if(장바구니가 없으면)
            - 장바구니가 없으면 빈 리스트을 return

        장바구니 상세 정보 리스트 찾아오기(카트id)
        return 장바구니 상세 정보 리스트

    */
    @Transactional(readOnly = true)
    public List<Cart_Detail_Dto> Get_Cart_List(String email)
    {
        List<Cart_Detail_Dto> cart_detail_dto_list = new ArrayList<>();

        Member member = member_repository.findByEmail(email);
        Cart cart = cart_repository.findByMember(member.getId());

        if(cart == null)
        {
            return cart_detail_dto_list;
        }

        cart_detail_dto_list = cart_item_repository.findCartDetailDtoList(cart.getId());
        return cart_detail_dto_list;
    }

    /*
        TODO 장바구니 접근시 권한이 있는 유저가 정상적으로 접근 했는지
        현재 로그인한 유저 찾기(이메일)
        장바구니 상품 정보 찾기(장바구니 상품 id)
        상품을 저장한 유저 찾기 - 장바구니 상품.get장바구니.get유저

        if(현재 유저.이메일과 저장한 유저.이메일이 다르면)
            return false

        return true
    */
    @Transactional(readOnly = true)
    public boolean Validate_Cart_Item(Long cart_item_id,String email)
    {
        Member cur_member = member_repository.findByEmail(email);

        Cart_Item cart_item = cart_item_repository.findById(cart_item_id)
                .orElseThrow(EntityNotFoundException::new);
        Member saved_member = cart_item.getCart().getMember();

        if(!StringUtils.equals(cur_member.getEmail(),saved_member.getEmail()))
        {
            return false;
        }

        return true;
    }

    public void Update_Cart_Item_Count(Long cart_item_id,int count)
    {

    }
}
