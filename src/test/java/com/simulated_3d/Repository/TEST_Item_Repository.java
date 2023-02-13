package com.simulated_3d.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Entity.Product.QItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TEST_Item_Repository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    Item_Repository item_repository;

    Item_Repository_Custom_Impl item_repository_custom_impl;

//    TODO 메소드
    public void Create_Item_List()
    {
        for(int i = 1; i <= 10; i++)
        {
            Item item = new Item();
            item.setName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setDetail("테스트 상품 상세 설명" + i);
            item.setSell_status(Sell_Status.SELL);
            item.setStock_number(100+i);
            item.setReg_time(LocalDateTime.now());
            item.setUpdate_time(LocalDateTime.now());

            Item saved_item = item_repository.save(item);
        }
    }

//    TODO 테스트
    @Test
    @DisplayName("QueryDSL 조회 테스트 - 1")
    public void QueryDSL_Test_01()
    {
        this.Create_Item_List();
        JPAQueryFactory query_factory = new JPAQueryFactory(em);

        QItem qitem = QItem.item;

        JPAQuery<Item> query = query_factory.selectFrom(qitem)
                .where(qitem.sell_status.eq(Sell_Status.SELL))
                .where(qitem.detail.like("%"+"테스트 상품 상세 설명" + "%"))
                .orderBy(qitem.price.desc());

        List<Item> item_list = query.fetch();

        for(Item item : item_list)
        {
            log.debug(item.getName());
        }

    }
    @Test
    @DisplayName("QueryDSL 조회 테스트 - 2")
    public void QueryDSL_Test_02()
    {
        this.Create_Item_List();
        JPAQueryFactory query_factory = new JPAQueryFactory(em);

        Item_Search_Dto item_search_dto = new Item_Search_Dto("all",Sell_Status.SELL,"name","테스트");
        Pageable pageable = PageRequest.of(0,10);
        QItem qitem = QItem.item;

        List<Item> content = query_factory
                .selectFrom(qitem)
                .where(item_repository_custom_impl.Reg_Time_After("all"))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.debug("길이는" + content.size());
        for(Item item : content)
        {
            log.debug(item.getName());
        }
    }

    @Test
    @DisplayName("일반적인 테스트 라인")
    public void Common_Test()
    {
        Item_Search_Dto item_search_dto = new Item_Search_Dto("6m",Sell_Status.SELL,"name","테스트");

        log.debug(item_search_dto.getDate_type());
    }

//    TODO 메소드


}
