package com.simulated_3d.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@AllArgsConstructor
public class Alert_Dto {

    private String message;
    private int type;
    /*
     1 - 에러 , 새로고침
     2 - 선택형, 리다이렉트
     3 - 메시지, 리다이렉트
     4 - 메시지만 출력
    */
    private String redirect_url;
    private Map<String,Object> data;
    private RequestMethod method;
}
