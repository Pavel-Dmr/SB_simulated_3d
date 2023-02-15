package com.simulated_3d.Repository;

import com.simulated_3d.DTO.Order_Dto;
import com.simulated_3d.Entity.Product.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Item_Repository extends JpaRepository<Item,Long>,QuerydslPredicateExecutor<Item> ,Item_Repository_Custom{


    List<Item> findByName(String name);

    List<Item> findByNameOrDetail(String name,String detail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);


    @Query("select i from Item i where i.detail like " + "%:detail% order by i.price desc")
    List<Item> findByDetail(@Param("detail") String detail);

    @Query(value = "select i from Item i where i.detail like " + "%:detail% order by i.price desc", nativeQuery = true)
    List<Item> findByDetailByNative(@Param("detail") String detail);


}
