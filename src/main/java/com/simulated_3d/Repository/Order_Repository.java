package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Order_Repository extends JpaRepository<Order,Long> {

    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.date desc"
    )
    List<Order> Find_Order_List(@Param("email") String email, Pageable pageable);
    //현재 로그인한 사용자의 주문 데이터를 페이지 조건에 맞추어서 조회


    @Query("select count(o) from Order o " +
            "where o.member.email = :email"
    )
    Long Count_Order(@Param("email") String email);
    //현재 로그인한 회원의 주문 개수가 몇 개인지 조회
}
