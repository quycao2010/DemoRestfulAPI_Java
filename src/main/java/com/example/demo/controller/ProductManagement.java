package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductValidator;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class ProductManagement {

    private final ProductService productService;

    @Autowired
    private ProductValidator validator;

    @Autowired
    public ProductManagement(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<HashMap<String, Object>> getAllProduct(){
        HashMap<String, Object> customResponse = new HashMap<>();
        List<Product> products = productService.findAllProducts();
        customResponse.put("message","success");
        customResponse.put("products",products);
        if(customResponse.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<HashMap<String, Object>>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id){
        Optional<Product> product = productService.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, UriComponentsBuilder builder){
        if (validator.isValid(product)){
            productService.save(product);
        }else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/products/{id}").buildAndExpand(product.getID()).toUri());
        return  new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable("id") Integer id, @RequestBody Product product){
        Optional<Product> currentProduct = productService.findById(id);
        if (!currentProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!product.getName().equals("")){
            currentProduct.get().setName(product.getName());
        }else if (!product.getDescription().equals("")){
            currentProduct.get().setDescription(product.getDescription());
        }else if (product.getPrice() != 0){
            currentProduct.get().setPrice(product.getPrice());
        }
        productService.save(currentProduct.get());
        return new ResponseEntity<>(currentProduct.get(),HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id){
        Optional<Product> currentProduct = productService.findById(id);
        if (!currentProduct.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(currentProduct.get());
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
