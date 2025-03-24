package com.machado.controllers;


import com.machado.entity.Product;
import com.machado.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    public ProductRepo productRepo;

    /***
     * Cacheable helps to store in cache, key is the unique identifier
     * value is the result of the redisHash name provided
     * Conditional caching are those where you mention conditions for which is there is a match
     * unless - (Spring Expression Language) if false , then don't store it in the cache
     * condition - (Spring Expression Language) if true, then cache
     */
    @Cacheable(key = "#id", value = "Product", unless = "#value.price > 10", condition = "#value.name.equals('Vicky') && #value.id != null")
    @GetMapping("/{id}")
    public Product getProductByID(@PathVariable Long id){
        return productRepo.getProductById(id);
    }


    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product){
        return productRepo.saveProduct(product);
    }

    /***
     * Eviction Happens one this function is hit,
     * before invocation makes sure the cache object is removed before the function execution starts
     * all enteries deletes every single object in cache
     */
    @CacheEvict(
            key = "#id",
            value = "Product"
            /*,
            beforeInvocation = true,
            allEntries = true
            */
    )
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        return productRepo.deleteProductById(id);
    }

    /***
     * If multiple cache settings are required on one function
     * we can use Caching with different parameters to perform
     * the required caching
     */
    @Caching(
            cacheable = { @Cacheable(value = "Product", key = "#id") },
            put = { @CachePut(value = "Product", key = "#result.id") },
            evict = { @CacheEvict(value = "Product") }
    )
    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productRepo.getAllProducts();
    }

    /***
     * @CachePut(value = "Product", key = "#product.id")
     */

}
