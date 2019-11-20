package com.example.demo.config;

import com.example.demo.model.ProductValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {
    @Bean
    public ProductValidator validate(){
        return new ProductValidator();
    }
}
