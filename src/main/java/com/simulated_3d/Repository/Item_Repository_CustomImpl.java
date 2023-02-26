package com.simulated_3d.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simulated_3d.Constant.Sell_Status;
import com.simulated_3d.DTO.Item_Dto;
import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.DTO.Main_Item_Dto;
import com.simulated_3d.DTO.QMain_Item_Dto;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Entity.Product.QItem;
import com.simulated_3d.Entity.Product.QItem_Img;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class Item_Repository_CustomImpl implements Item_Repository_Custom{

    private final JPAQueryFactory query_factory;

    BooleanBuilder Search_Builder(Item_Search_Dto item_search_dto)
    {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(Reg_Time_After(item_search_dto.getDate_type()));
        builder.and(Search_Sell_Status(item_search_dto.getSell_status()));
        builder.and(Search_By_Like(item_search_dto.getSearch_by(), item_search_dto.getSearch_query()));

        return builder;
    }

    /*
        상품 판매 상태에 따른 조건 분류
    */
    BooleanExpression Search_Sell_Status(Sell_Status sell_status)
    {
        return sell_status == null ?  null : QItem.item.sell_status.eq(sell_status);
    }

    /*
        상품 등록 시간에 따른 조건 분류
    */
    BooleanExpression Reg_Time_After(String reg_time)
    {
        LocalDateTime date_time = LocalDateTime.now();


        if(StringUtils.equals("all",reg_time) || reg_time == null)
        {
            return  null;
        }

        int num = Integer.parseInt(reg_time.substring(0,reg_time.length()-1));
        char type = reg_time.charAt(reg_time.length()-1);

        switch (type)
        {
            case 'd':
                date_time = date_time.minusDays(num);
                break;
            case 'w':
                date_time =date_time.minusWeeks(num);
                break;
            case 'm':
                date_time = date_time.minusMonths(num);
                break;
            case 'y':
                date_time = date_time.minusYears(num);
                break;
        }

        return QItem.item.reg_time.after(date_time);
    }

    /*
        무엇을 검색하는지의 종류을 기준으로 분류
        - 상품명 기준
        - 글쓴이 (생성자) 기준
    */
    BooleanExpression Search_By_Like(String search_by, String search_query)
    {
        if(StringUtils.isEmpty(search_query))
        {
            return null;
        }
        else {
            switch (search_by) {
                case "name":
                    return QItem.item.name.like("%" + search_query + "%");
                case "created_by":
                    return QItem.item.created_by.like("%" + search_query + "%");
                default:
                    return null;
            }
        }

    }

    /*
         검색어 기준으로 분류
    */
    private BooleanExpression Item_Name_Like(String search_query)
    {
        return StringUtils.isEmpty(search_query) ?  null : QItem.item.name.like("%" + search_query + "%");
    }



//    TODO 오버라이드 ===============
    /*
        검색 기준에 비해 분류 되어, 어떤 상품들을 가져와야하는지 탐색하는 메서드 ( 판매 상태 , 등록 시간, 상품명, 글쓴이(생성자) )
    */
    @Override
    public Page<Item> getAdminItemPage(Item_Search_Dto item_search_dto, Pageable pageable)
    {

//        게시물 정보을 리스트로 반환
        List<Item> content = query_factory
                .selectFrom(QItem.item)
                .where(Search_Builder(item_search_dto))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        검색 조건에 맞는 게시물의 갯수을 반환
        long total = query_factory.select(Wildcard.count).from(QItem.item)
                .where(Search_Builder(item_search_dto))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }


    /*
        홈페이지 메인 페이지에 노출할 상품 목록을 받아옵니다
    */
    //@Override
    public Page<Main_Item_Dto> getMainItemPage(Item_Search_Dto item_search_dto, Pageable pageable) {


        QItem item = QItem.item;
        QItem_Img item_img = QItem_Img.item_Img;
        List<Main_Item_Dto> content = query_factory
                .select(
                        new QMain_Item_Dto(
                                item.id,
                                item.name,
                                item.detail,
                                item_img.url,
                                item.price)
                )
                .from(item_img)
                .join(item_img.item,item)
                .where(item_img.main.eq(true))
                .where(Search_Builder(item_search_dto))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query_factory
                .select(Wildcard.count)
                .from(item_img)
                .join(item_img.item,item)
                .where(item_img.main.eq(true))
                .where(Item_Name_Like(item_search_dto.getSearch_query()))
                .fetchOne();



       return new PageImpl<>(content,pageable,total);
    }

    /*
        1개의 상품에 대한 이미지 목록 리턴
    */
    @Override
    public List<String> getImgUrlList(Item_Dto item_dto) {

        List<String> content = query_factory
                .select(QItem_Img.item_Img.url)
                .from(QItem_Img.item_Img)
                .where(QItem_Img.item_Img.item.id.eq(item_dto.getId()))
                .orderBy(QItem_Img.item_Img.id.desc())
                .fetch();

      return content;
    }


}
