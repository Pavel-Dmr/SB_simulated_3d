package com.simulated_3d.Controller;

import com.simulated_3d.DTO.Member_Dto;
import com.simulated_3d.Entity.Member;
import com.simulated_3d.Service.Member_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class Member_Controller {

    private final Member_Service member_service;

    private final PasswordEncoder password_encoder;


    @GetMapping(value = "/new")
    public String Member_Sign(Model model)
    {
        model.addAttribute("memder_dto",new Member_Dto());
        return "Member/Member_Sign";
    }

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

    @GetMapping(value = "/login")
    public String Member_Login(Model model)
    {

        return "Member/Member_Login";
    }

    @GetMapping(value = "/login/error")
    public String Member_Login_Error(Model model)
    {
        model.addAttribute("login_error","아이디와 비번을 다시 확인해주세요.");
        return "Member/Member_Login";
    }

}
