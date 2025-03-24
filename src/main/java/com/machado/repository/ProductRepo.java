package com.machado.repository;


import com.machado.entity.Product;
import com.machado.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepo {

    public static final String HASH_KEY = "Product";
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public String saveProduct(Product product){
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
        return "Product saved successfully";
    }
    public Product getProductById(Long id){
        return (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
    }

    public String deleteProductById(Long id){
        redisTemplate.opsForHash().delete(HASH_KEY, id);
        return "Product deleted successfully";
    }
    public List<Product> getAllProducts(){
        return redisTemplate
                .opsForHash()
                .values(HASH_KEY)
                .stream()
                .map(obj -> (Product) obj)
                .collect(Collectors.toList());
    }
}
