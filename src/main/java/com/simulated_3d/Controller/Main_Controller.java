package com.simulated_3d.Controller;

import com.simulated_3d.DTO.Item_Search_Dto;
import com.simulated_3d.DTO.Main_Item_Dto;
import com.simulated_3d.Service.Item_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class Main_Controller {

    private final Item_Service item_service;

    /*
        메인 페이지(item_search_dto,page,model)

    */
    @GetMapping(value = "/")
    public String Main(Item_Search_Dto item_search_dto, Optional<Integer> page, Model model)
    {
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 ,6);
//        Page<Main_Item_Dto> items = item_service.getMainItemPage(item_search_dto,pageable);
//        int max_page = 5;
//        model.addAttribute("items",items);
//        model.addAttribute("item_search_dto",item_search_dto);
//        model.addAttribute("max_page",max_page);

        return "Main";
    }


}
