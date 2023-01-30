package com.simulated_3d.Controller;

import com.simulated_3d.DTO.Item_Dto;
import com.simulated_3d.DTO.Item_Img_Dto;
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
        @상품 생성 페이지로 이동
    */
    @GetMapping(value = "/admin/new")
    public String Item_Form(Model model)
    {
        model.addAttribute("item_dto",new Item_Dto());
        return "Item/Item_Form";
    }

    /*
        @상품 생성을 submit해서 아이템을 등록 시킵니다
        @Valid로 전달 받은 데이터에 오류가 있으면 BindResult가 오류을 갖게됨. 다시 돌려보냄
        상품 이미지가 없거나,id가 없으면 다시 돌려보냄
        try
            item_service에 item을 저장합니다. (아이템_dto와 아이템_이미지_파일_리스트)을 매개변수로 주어야합니다
        catch
            상품 등록 중 에러 발생 메시지. 다시 돌려보냄
        success
        이전 페이지로 이동
    */
    @PostMapping(value = "/admin/new")
    public String Item_New(@Valid Item_Dto item_dto, BindingResult binding_result,
                           Model model, @RequestParam("item_img_file") List<MultipartFile> item_img_file_list){


        if(binding_result.hasErrors()){
            log.debug("바인딩 오류");
            return "Item/Item_Form";
        }

        if(item_img_file_list.get(0).isEmpty() && item_dto.getId() == null){
            model.addAttribute("error_message", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            log.debug("이미지 파일이 없음");
            return "Item/Item_Form";
        }

        try {
            item_service.Save_Item(item_dto, item_img_file_list);
        } catch (Exception e){
            log.debug("상품 저장중 에러 발생");
            model.addAttribute("error_message", "상품 등록 중 에러가 발생하였습니다.");
            return "Item/Item_Form";
        }

        return "redirect:/";
    }

    /*
        @상품 수정 페이지로 이동
        url 변수 item_id / model
        try
             view에 보여주기 위해서 item_id로 해당 상품 정보을 찾아서 item_dto로 생성
             item_dto 모델로 등록
         catch
            존재하지 않는 상품 에러 메시지
            item_dto 빈 객체 생성해 모델로 등록
            상품 생성 페이지로 리턴
        success
            찾은 item_dto로 상품 생성 페이지을 리턴(생성 페이지가 수정페이지도 겸함)
    */
    @GetMapping(value = "/admin/{item_id}")
    public String Item_Show(@PathVariable("item_id") Long item_id, Model model){


        try
        {
            Item_Dto item_dto = item_service.Get_ItemDTL(item_id);
            model.addAttribute("item_dto", item_dto);
        }
        catch(EntityNotFoundException e)
        {
            log.debug("존재하지 않는 상품 에러");
            model.addAttribute("item_dto", new Item_Dto());
            return "Item/Item_Form";
        }



        return  "Item/Item_Form";
    }

    /*
         @상품 수정 페이지에서 submit시
         item_dto / binding_result / item_img_file_list / model
         if - item_dto 객체 데이터 오류시
                다시 돌려보냄.
         if - item_img_file_list 첫번째 인덱스가 비거나, item_dto의 id가 null이면
            에러메시지 - 이미지 하나는 필수 입력
            다시 돌려보냄
        try
            상품 업데이트 요청(item_dto,item_img_file_list)
        catch
            에러메시지 - 수정 중 에러 발생
            다시 돌려보냄
         success
            이전 페이지로 리턴

    */
    @PostMapping(value = "/admin/{item_id}")
    public String itemUpdate(@Valid Item_Dto item_dto, BindingResult binding_result,
                             @RequestParam("item_img_file") List<MultipartFile> item_img_files, Model model){

        List<Long> test = item_dto.getItem_img_ids();
        log.debug("테스트 중");

        for(Long tes : test)
        {
            log.debug(String.valueOf(tes));
        }

        if(binding_result.hasErrors()){
            model.addAttribute("error_message","바인딩 에러");
            log.debug("바인딩 에러");
            return "Item/Item_Form";
        }

        if(item_img_files.get(0).isEmpty() || item_dto.getId() == null){
            model.addAttribute("error_message","상품 첫 번째 이미지는 필수 요소 입니다.");
            log.debug("이미지 오류");
            return "Item/Item_Form";
        }


        try
        {
            item_service.Update_Item(item_dto, item_img_files);
        }
        catch (Exception e){
            model.addAttribute("error_message","이미지 업데이트 도중 에러 발생");
            log.debug("업데이트 오류");
            return "Item/Item_Form";
        }

        return "redirect:/";
    }

    /*
        일반 사용자가 보는 아이템 상세 정보 페이지
        model / @pv item_id
        item_id로 view에 보여주는 필요한 정보을 item_dto로 생성
        item_dto 모델로 설정
        상품 정보 페이지(일반 사용자용)로 이동
    */
    @GetMapping(value = "/{item_id}")
    public String itemDtl(Model model, @PathVariable("item_id") Long item_id){
        Item_Dto item_dto = item_service.Get_ItemDTL(item_id);
        model.addAttribute("item_dto", item_dto);
        return "Item/Item_View";
    }

    @GetMapping(value = "/admin/list")
    public String Item_List(Model model)
    {
        return "Item/Item_List";
    }
}
