package com.simulated_3d.Controller;

import com.simulated_3d.DTO.*;
import com.simulated_3d.Entity.Product.Item;
import com.simulated_3d.Service.Item_Img_Service;
import com.simulated_3d.Service.Item_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/item")
@RequiredArgsConstructor
@Slf4j
public class Item_Controller {

    private final Item_Service item_service;
    private final Item_Img_Service item_img_service;

    /*
        Permit_All
        특정 상품 상세 페이지
        (model / @pv item_id)
        item_id로 view에 보여주는 필요한 정보을 item_dto로 생성
        item_dto 모델로 설정
        특정 상품 상세 페이지로 이동
    */
    @GetMapping(value = "/{item_id}")
    public String Item_View(Model model, @PathVariable("item_id") Long item_id){

        Item_Dto item_dto = item_service.Get_Item(item_id);
        List<String> img_url_list = item_service.getImgUrlList(item_dto);
        Order_Dto order_dto = new Order_Dto(item_id,1);

        model.addAttribute("item_dto", item_dto);
        model.addAttribute("img_url_list", img_url_list);
        model.addAttribute("order_dto", order_dto);
        return "Item/Item_View";
    }


    /*
        Permit_All
        상품 목록 페이지(item_search_dto,@PV page,model)
        TODO 템플릿 생성 필요
     */
   @GetMapping(value = {"/list","/list/{page}"})
    public String Item_list(Item_Search_Dto item_search_dto,@PathVariable("page") Optional<Integer> page,Model model)
   {
       Pageable pageable = PageRequest.of(page.orElse(0), 8);
       Page<Main_Item_Dto> item_page = item_service.getMainItemPage(item_search_dto, pageable);

       model.addAttribute("item_page", item_page);
       model.addAttribute("item_search_dto", item_search_dto);
       model.addAttribute("max_page", 5);

       return "Item/Item_List";
   }

}
