package com.simulated_3d.Controller;

import com.simulated_3d.DTO.Member_Dto;
import com.simulated_3d.Entity.Member;
import com.simulated_3d.Service.Member_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller

@Slf4j
public class Member_Controller extends Alert_Controller{

    @Autowired
    private final Member_Service member_service;

    private final PasswordEncoder password_encoder;


    /*
        @회원가입 합니다
        model
        member_dto 모델 등록
        return Member_Sign.html
    */
    @GetMapping(value = "/new")
    public String Member_Sign(Model model)
    {
        model.addAttribute("member_dto",new Member_Dto());
        return "Member/Member_Sign";
    }

    /*
        @회원가입 등록 요청
         member_dto , binding_result , model
         if - 데이터 바인딩시 에러가 있으면
            return 다시 폼으로 돌려보냄
        try
            member 생성 ( member_dto ,password_encoder);
            member 저장
        catch
            에러메시지 - 모델 등록
            return 다시 폼으로 돌려보냄
        success
            return 이전 페이지로 돌아갑니다
    */
    @PostMapping(value = "/new")
    public String Member_Sign(@Valid Member_Dto member_dto, BindingResult binding_result, Model model)
    {
        if(binding_result.hasErrors())
        {
            return "Member/Member_Sign";
        }

        try
        {
            Member member = Member.Create_Member(member_dto,password_encoder);
            member_service.Save_Member(member);
        }
        catch(IllegalStateException e)
        {
            model.addAttribute("error_message",e.getMessage());
            return "Member/Member_Sign";
        }

        return "redirect:/";

    }

    /*
        @로그인 합니다
        인자 없음
        return  Member_Login.html
    */
    @GetMapping(value = "/login")
    public String Member_Login()
    {
        return "Member/Member_Login";
    }

    /*
        @로그인 도중 에러 발생
         model / error / exception
         error 모델 등록
         exception 모델 등록
         return Member_Login.html
    */
    @GetMapping(value = "/login/error")
    public String Member_Login_Error(Model model,@RequestParam(value = "error")boolean error,@RequestParam(value = "exception") String exception)
    {
        model.addAttribute("error",error);
        model.addAttribute("exception",exception);
        return "Member/Member_Login";
    }



//    중복 체크 - 닉네임, 이메일
    @PostMapping( value = "/nickname_check" )
    public @ResponseBody ResponseEntity Check_Nickname_Duplicate(@RequestBody HashMap<String,String> nickname)
    {

        if(member_service.Check_Nickname(nickname.get("data")))
        {
            return new ResponseEntity<>("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }

        log.debug(nickname.get("data"));

        return new ResponseEntity<>(nickname,HttpStatus.OK);
    }

    @PostMapping( value = "/email_check" )
    public @ResponseBody ResponseEntity Check_Email_Duplicate(@RequestBody HashMap<String,String> email)
    {

        if(member_service.Check_Nickname(email.get("data")))
        {
            return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        log.debug(email.get("data"));

        return new ResponseEntity<>(email,HttpStatus.OK);
    }

}
