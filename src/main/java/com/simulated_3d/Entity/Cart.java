package com.simulated_3d.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Cart extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /* TODO 연관관계 필드 */
    @OneToOne(fetch = FetchType.EAGER)
    private Member member;

    /* TODO 연관관계 메서드 */

    /*
        @카트을 생성합니다
        member
        cart 생성 ( new)
        cart.member을 설정(member 통해서)
        return cart;
    */
    public static Cart Create_Cart(Member member)
    {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }


}
