package com.simulated_3d.Controller;

import com.simulated_3d.DTO.Member_Dto;
import com.simulated_3d.Entity.Member;
import com.simulated_3d.Service.Member_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class Member_Controller {

    @Autowired
    private final Member_Service member_service;

    private final PasswordEncoder password_encoder;


    @GetMapping(value = "/new")
    public String Member_Sign(Model model)
    {
        model.addAttribute("member_dto",new Member_Dto());
        return "Member/Member_Sign2";
    }

    @PostMapping(value = "/new")
    public String Member_Sign(@Valid Member_Dto member_dto, BindingResult binding_result, Model model)
    {
        if(binding_result.hasErrors())
        {
            return "Member/Member_Sign2";
        }

        try
        {
            Member member = Member.Create_Member(member_dto,password_encoder);
            member_service.Save_Member(member);
        }
        catch(IllegalStateException e)
        {
            model.addAttribute("error_message",e.getMessage());
            return "Member/Member_Sign2";
        }

        return "redirect:/";

    }

    @GetMapping(value = "/login")
    public String Member_Login(Model model)
    {

        return "Member/Member_Login";
    }

    @GetMapping(value = "/login/error")
    public String Member_Login_Error(Model model)
    {
        model.addAttribute("error_message","아이디와 비번을 다시 확인해주세요.");
        return "Member/Member_Login";
    }

//    중복 체크 - 닉네임, 이메일
    @PostMapping("/{nickname}/check")
    public @ResponseBody ResponseEntity Check_Nickname_Duplicate(@PathVariable("nickname") String nickname)
    {
        if(!member_service.Check_Nickname(nickname))
        {
            return new ResponseEntity<String>("이미 사용중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(nickname,HttpStatus.OK);
    }

    @PostMapping("/{email}/check")
    public @ResponseBody ResponseEntity Check_Email_Duplicate(@PathVariable("email") String email)
    {
        if(!member_service.Check_Email(email))
        {
            return new ResponseEntity<String>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(email,HttpStatus.OK);
    }

}
