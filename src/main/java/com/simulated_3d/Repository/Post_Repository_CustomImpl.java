package com.simulated_3d.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simulated_3d.Entity.Product.Post;
import com.simulated_3d.Entity.Product.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class Post_Repository_CustomImpl implements Post_Repository_Custom {

   private final JPAQueryFactory query_factory;

    @Override
    public List<Post> findMyPost() {
        QPost post = QPost.post;
        List<Post> post_list = query_factory
                .selectFrom(post)
                .where(post.title.like("%" + "포스트" + "%"))
                .orderBy(post.id.desc())
                .fetch();

        return post_list;
    }
}
