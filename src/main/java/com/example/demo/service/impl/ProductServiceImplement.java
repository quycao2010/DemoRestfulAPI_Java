package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.model.ProductValidator;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    public ProductServiceImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer ID) {
        return productRepository.findById(ID);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Product product) {
        productRepository.delete(product);
    }
}
