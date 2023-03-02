package com.simulated_3d.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulated_3d.DTO.Alert_Dto;
import com.simulated_3d.DTO.Order_Dto;
import com.simulated_3d.DTO.Order_Hist_Dto;
import com.simulated_3d.Service.Order_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor

public class Order_Controller extends Alert_Controller{

    private final Order_Service order_service;

    /*
        @ 상품을 주문합니다
        order_dto / binding_result / principal
        if - 데이터 바인딩 에러시
            binding_result.getFieldErrors 에러 정보 호출해 sb에 담아 return Response 합니다
        현재 접속한 사용자의 이메일 email
        order_id을 생성
        try
            order_dto , email로 주문 요청하고 order_id 선언
        catch
            배드 리퀘스트,예외 메시지 리턴
        success

    */
    @PostMapping(value = "/new")
    public String Single_Order(@Valid Order_Dto order_dto
            , BindingResult binding_result, Principal principal,Model model){


        if(binding_result.hasErrors())
        {
            StringBuilder sb = new StringBuilder();
            List<FieldError> field_error_list = binding_result.getFieldErrors();

            for (FieldError field_error : field_error_list) {
                sb.append(field_error.getDefaultMessage());
            }

            Alert_Dto alert_dto = new Alert_Dto("잘못된 요청입니다 \n 오류 원인 :" + sb.toString() +"\n 응답코드 : " + HttpStatus.BAD_REQUEST,1,null,null,RequestMethod.GET);
            return Alert_And_Redirect(alert_dto,model);
        }

        String email = principal.getName();

        Long order_id;

        try {
            order_id = order_service.Order(order_dto, email);
        }
        catch(Exception e){
            Alert_Dto alert_dto = new Alert_Dto("잘못된 요청입니다. \n 오류 원인 :" + e.getMessage() +"\n 응답코드 : " + HttpStatus.BAD_REQUEST,
                    1,null,null,RequestMethod.GET);
            return Alert_And_Redirect(alert_dto,model);
        }

        Alert_Dto alert_dto = new Alert_Dto("정상적으로 주문 되었습니다.",
                3,"redirect:/",null,RequestMethod.GET);
        return Alert_And_Redirect(alert_dto,model);
    }


    /*
        @주문 이력을 호출합니다
        page / principal / model
        pageable 주문 가져올 데이터 집합의 갯수을 설정 ( 5 설정 )
        order_hist_dtos 주문 목록 page의 형태로 생성 ( 현재 로그인한 이메일,pageable);
        주문 이력 정보를 가진 order_hist_dtos 모델 등록
        현재 페이지 정보를 가진 page 등록

    */
    @GetMapping(value = {"/list", "/list/{page}"})
    public String Order_Hist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        //한번에 가지고 올 수 있는 주문

        Page<Order_Hist_Dto> order_hist_dtos = order_service.Get_Order_Page(principal.getName(), pageable);

        model.addAttribute("order_hist_dtos", order_hist_dtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("max_page", 5);

        return "/Order_Hist";
    }


    /*
        @주문을 취소합니다
         order_id / principal

         if - 주문 무결성 체크 ( order_id / principal.getName()) 주문자의 이메일과 현재 로그인한 이메일이 같은지 체크
            return 에러메시지 - 주문 취소 권한이 없음
        success
            주문 취소 ( order_id 통해서)
            return 성공적으로 취소됨 OK
    */
    @PostMapping("/cancle/{order_id}")
    public String Cancel_Order(@PathVariable("order_id") Long order_id , Principal principal,Model model){

        if(!order_service.Validate_Order(order_id, principal.getName())){
            Alert_Dto alert_dto = new Alert_Dto("주문 취소 권한이 없습니다. \n 응답 코드 : " + HttpStatus.FORBIDDEN,1,null,null,RequestMethod.GET);
            return Alert_And_Redirect(alert_dto,model);
        }

        order_service.Cancel_Order(order_id);
        Alert_Dto alert_dto = new Alert_Dto("정상적으로 취소 되었습니다.",1,null,null,RequestMethod.GET);

    }


}
