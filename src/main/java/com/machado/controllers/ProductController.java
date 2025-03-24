package com.machado.controllers;


import com.machado.entity.Product;
import com.machado.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    public ProductRepo productRepo;

    @GetMapping("/{id}")
    public Product getProductByID(@PathVariable Long id){
        return productRepo.getProductById(id);
    }

    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product){
        return productRepo.saveProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productRepo.deleteProductById(id);
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productRepo.getAllProducts();
    }

}
