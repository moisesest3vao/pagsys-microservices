package br.com.pagsys.msusers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanConfig{
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
