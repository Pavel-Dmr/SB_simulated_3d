package com.simulated_3d.Repository;

import com.simulated_3d.Entity.Product.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TEST_Post_Repository {

    @Autowired
    EntityManager em;

    @Autowired
    private Post_Repository post_repository;

    private void Create_Post()
    {
        for(int i = 1 ; i <= 10; i++)
        {
            Post post = new Post();
            post.setTitle("포스트" + i);
            Post save_post = post_repository.save(post);
        }
    }

    @Test
    public void Post_Test()
    {
        this.Create_Post();

        List<Post> post_list = post_repository.findMyPost();

        for(Post post : post_list)
        {
            log.debug(post.getTitle());
        }
    }
}
