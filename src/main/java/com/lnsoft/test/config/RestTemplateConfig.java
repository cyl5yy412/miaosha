package com.lnsoft.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created By Chr on 2019/4/7/0007.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRest() {
        return new RestTemplate();
    }
}
