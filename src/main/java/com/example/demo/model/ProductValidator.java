package com.example.demo.model;

import org.springframework.util.StringUtils;

import java.util.Optional;

public class ProductValidator {
    public boolean isValid(Product product){
        return Optional.ofNullable(product)
                        .filter(t -> !StringUtils.isEmpty(t.getName()))
                        .filter(t -> !StringUtils.isEmpty(t.getDescription()))
                        .filter(t -> !StringUtils.isEmpty(t.getPrice()))
                        .isPresent();
    }
}
