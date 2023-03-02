package com.simulated_3d.Repository;


import com.simulated_3d.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Cart_Repository extends JpaRepository<Cart,Long> {

    Cart findByMember(Long member_id);
}
