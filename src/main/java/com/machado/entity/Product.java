package com.machado.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("Product" /*timeToLive = 1L default */)
public class Product implements Serializable {

    @Id
    private long id;

    private String name;

    private String description;

    private int quantity;

    private int price;

}
