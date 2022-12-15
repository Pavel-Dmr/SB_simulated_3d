package com.simulated_3d.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class Auditor_Config {

    @Bean
    public AuditorAware<String> Auditor_Provider()
    {
        return new Auditor_Aware();
    }
}
