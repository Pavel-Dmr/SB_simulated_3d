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

    private boolean email_check = false;
    private boolean nickname_check = false;

    public Member Save_Member(Member member)
    {
        if(!nickname_check && !email_check)
        {
            throw new IllegalStateException("닉네임과 이메일 중복체크를 진행해주세요.");
        }
        else if(!email_check)
        {
            throw new IllegalStateException("이메일 중복체크을 진행 해주세요.");
        }
        else if(!nickname_check)
        {
            throw new IllegalStateException("닉네임 중복체크을 진행 해주세요.");
        }

        return member_repository.save(member );
    }

    private void Validate_DuplicateNickname(Member member)
    {
        Member find_member = member_repository.findByNickname(member.getNickname());

        if(find_member != null)
        {
            nickname_check = false;
            throw new IllegalStateException("중복된 닉네임입니다.");
        }
        else
        {
            nickname_check= true;
        }
    }
//    회원가입 중복 확인 ( 이메일 중복 )
    private void Validate_DuplicateMember(Member member)
    {
        Member find_member = member_repository.findByEmail(member.getEmail());

        if(find_member != null)
        {
            email_check = false;
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        else
        {
            email_check = true;
        }
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
}
