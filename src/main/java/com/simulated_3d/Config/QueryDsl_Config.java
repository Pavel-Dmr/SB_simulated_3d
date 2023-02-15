package com.simulated_3d.Config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QueryDsl_Config {

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory query_factory()
    {
        return new JPAQueryFactory(em);
    }
}
