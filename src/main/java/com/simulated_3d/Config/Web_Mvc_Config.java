package com.simulated_3d.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Web_Mvc_Config implements WebMvcConfigurer {

    @Value("${upload_path}") // 프로퍼티 값을 읽어옵니다.
    String upload_path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(upload_path);

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/");


//        로컬 컴퓨터에 저장된 파일을 읽어올 root 경로 설정

        //웹 브라우저에 입력하는 url에 /images로 시작하는 경우 upload_path에 설정한
        // 폴더를 기준으로 파일을 읽어 오도록 설정합니다.

    }
}
