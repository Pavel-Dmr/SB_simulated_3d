package com.simulated_3d.Service;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

@Service
public class Login_Fail_Service extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {


        String error_message;
        if(exception instanceof AuthenticationServiceException) {
            error_message = "죄송합니다. 시스템에 오류가 발생했습니다.";
        }
        else if(exception instanceof UsernameNotFoundException)
        {
            error_message = "가입되지 않은 이메일입니다.";
        }
        else if(exception instanceof BadCredentialsException) {
            error_message = "아이디 또는 비밀번호가 일치하지 않습니다.";
        }
        else if(exception instanceof DisabledException) {
            error_message = "현재 사용할 수 없는 계정입니다.";
        }
        else if(exception instanceof LockedException) {
            error_message ="현재 잠긴 계정입니다.";
        }
        else if(exception instanceof AccountExpiredException) {
            error_message =  "이미 만료된 계정입니다.";
        }
        else if(exception instanceof CredentialsExpiredException) {
            error_message = "비밀번호가 만료된 계정입니다.";
        }
        else error_message = "계정을 찾을 수 없습니다.";

        error_message = URLEncoder.encode(error_message,"UTF-8");
        setDefaultFailureUrl("/member/login/error?error=true&exception=" + error_message);
        super.onAuthenticationFailure(request,response,exception);

    }
}