package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Member_Repository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);
    Member findByNickname(String nickname);
}
