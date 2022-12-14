package com.simulated_3d.Entity;

import com.simulated_3d.Constant.Role_Status;
import com.simulated_3d.DTO.Member_DTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private String role_status;

//    TODO  연관관계 필드

    @OneToMany(mappedBy = "member")
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> order_list = new ArrayList<>();

    public static Member Create_Member(Member_DTO member_dto, PasswordEncoder password_encoder)
    {
        Member member = new Member();
        member.setName(member_dto.getName());
        member.setNickname(member_dto.getNickname());
        member.setEmail(member_dto.getEmail());

//        암호화후 패스워드 저장
        String password = password_encoder.encode(member_dto.getPassword());
        member.setPassword(password);

        member.setRole_status(String.valueOf(Role_Status.USER));
        member.setAddress(member_dto.getAddress());

        return member;
    }
}
