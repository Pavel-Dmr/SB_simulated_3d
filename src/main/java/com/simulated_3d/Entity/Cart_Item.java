package com.simulated_3d.Entity;

import com.simulated_3d.Entity.Product.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Cart_Item extends Base{

    @Id
    @GeneratedValue
    private long id;

    private int count;

    //    연관관계 필드
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Item item;

    public static Cart_Item Create_Cart_Item(Cart cart,Item item,int count)
    {
        Cart_Item cart_item = new Cart_Item();
        cart_item.setCart(cart);
        cart_item.setItem(item);
        cart_item.setCount(count);

        return cart_item;
    }

    public void Add_Count(int count)
    {
        this.count += count;
    }

    public void Updae_Count(int count)
    {
        this.count = count;
    }



}
