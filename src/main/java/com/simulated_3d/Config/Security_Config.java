package com.simulated_3d.Config;

import com.simulated_3d.Service.Member_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Security_Config {

    @Autowired
    Member_Service member_service;


    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception
    {
        // 로그인
        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("member/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests() // 시큐리티 처리에 httpservletRequest를 이용한다는 의미
                //permitAll()이 붙은 경우의 경로는 로그인없이 경로 접근이 가능하다(permit-all 완전 퍼블릭한 API)
                .mvcMatchers("/css/**","/js/**","/img/**").permitAll()
                .mvcMatchers("/","/member/**","/item/**","/image/**").permitAll()
                // hasRole()이 붙은 경우의 경로는 로그인된 유저 권한이 ADMIN인 경우만 가능하다
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        // 인증 과정중에 예외 발생시 핸들링
        // 예외 발생시 authenticationEntryPoint 에서 로직을 처리합니다
        http.exceptionHandling()
                .authenticationEntryPoint(new  Custom_Authentication_EntryPoint());

        return http.build();
    }

    // 비밀번호 암호화 구현체 대입하고 빈으로 등록
    @Bean
    public PasswordEncoder Password_Encoder()
    {
        // BCrypt라는 해시 함수를 이용하여 패스워드를 암호화
        return new BCryptPasswordEncoder();
    }
}
