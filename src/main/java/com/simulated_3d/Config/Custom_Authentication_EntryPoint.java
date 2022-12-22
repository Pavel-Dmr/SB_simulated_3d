package com.simulated_3d.Config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증 처리 과정에서 예외가 발생한 경우 예외를 핸들링하는 인터페이스 입니다.
public class Custom_Authentication_EntryPoint implements AuthenticationEntryPoint {

    /*
        인증 처리에서 예외 발생시 해당 메소드가 실행
    */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");

    }
}
