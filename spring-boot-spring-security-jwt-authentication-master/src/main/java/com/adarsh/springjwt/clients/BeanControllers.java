package com.adarsh.springjwt.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class BeanControllers {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
