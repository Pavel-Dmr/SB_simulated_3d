package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Product.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface Post_Repository extends JpaRepository<Post,Long> , QuerydslPredicateExecutor<Post>, Post_Repository_Custom {


}
