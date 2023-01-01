package com.simulated_3d.Config;

import com.simulated_3d.Service.Login_Fail_Service;
import com.simulated_3d.Service.Member_Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security_Config {

    private final Member_Service member_service;
    private final Login_Fail_Service login_fail_service;
    /*
         로그인과정

         인증 과정중에 예외 발생시 핸들링
         예외 발생시 authenticationEntryPoint 에서 로직을 처리합니다
    */


    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception
    {

       auth.authenticationProvider(this.daoAuthenticationProvider());

        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/member/login/error")
                .failureHandler(login_fail_service)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests()
                .mvcMatchers("/css/**","/js/**","/img/**","/font/**").permitAll()
                .mvcMatchers("/","/member/**","/item/**","/image/**","/test").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();


        http.exceptionHandling()
                .authenticationEntryPoint(new  Custom_Authentication_EntryPoint());






        return http.build();
    }

    /*
        비밀번호 암호화 구현체 대입하고 빈으로 등록
        BCrypt라는 해시 함수를 이용하여 패스워드를 암호화
    */
    @Bean
    public PasswordEncoder Password_Encoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();

        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(member_service);
        bean.setPasswordEncoder(Password_Encoder());

        return bean;
    }
}
