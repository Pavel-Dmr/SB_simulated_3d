package com.simulated_3d.Entity;

import com.simulated_3d.Constant.Role_Status;
import com.simulated_3d.DTO.Address_Dto;
import com.simulated_3d.DTO.Member_Dto;
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
    private Role_Status role_status;

//   ================== 여기부터 연관관계 필드

    @OneToMany(mappedBy = "member")
    private List<Order> order_list = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Address> address_list = new ArrayList<>();

    public static Member Create_Member(Member_Dto member_dto, PasswordEncoder password_encoder)
    {
        Member member = new Member();
        member.setName(member_dto.getName());
        member.setNickname(member_dto.getNickname());
        member.setEmail(member_dto.getEmail());

        String password = password_encoder.encode(member_dto.getPassword());
        member.setPassword(password);

        member.setRole_status(Role_Status.USER);

        return member;
    }

    public void Add_Address(Address address)
    {
        address_list.add(address);
        address.setMember(this);
    }
}
