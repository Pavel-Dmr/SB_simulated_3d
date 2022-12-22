package com.simulated_3d.Service;

import com.simulated_3d.Entity.Member;
import com.simulated_3d.Repository.Member_Repository;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class Member_Service implements UserDetailsService {

    private final Member_Repository member_repository;

    public Member Save_Member(Member member)
    {
        return member_repository.save(member);
    }




//    스프링 시큐리티에서 유저디테일서비스를 구현하고 있는 클래스를 통해 로그인 구현
//    UserDetailService  인터페이스는 데이터베이스에서 회원 정보를 가져오는 역할을 담당
//    loadUserByUsername  메소드가 존재하며, 회원 정보를 조회하여 사용자의 정보와 권환을 갖는 UserDetails 인터페이스를 반환
//    UserDetails 스프링 시큐리티에서 회원 정보를 담기 위해서 사용하는 인터페이스

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = member_repository.findByEmail(email);

        if(member == null)
        {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole_status().toString())
                .build();
    }

    public Boolean Check_Nickname(String nickname) {
        return member_repository.existsByNickname(nickname);
    }
    public Boolean Check_Email(String email) {
        return member_repository.existsByEmail(email);
    }
}
