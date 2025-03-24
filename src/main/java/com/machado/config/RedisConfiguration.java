package com.machado.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);

        return new JedisConnectionFactory( configuration);

    }

    @Bean
    public RedisTemplate<String , Object> redisTemplate(){
        RedisTemplate<String , Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        //redis always stores binary data
        //serialization helps to convert data easily
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //only good for java based systems, not readable outside J applications
        //Jackson2JsonRedisSerializer stores in JSON format but needs higher space
        //GenericJackson2JsonRedisSerializer generic and works with multiple data types
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /***
     * Redis cluster configuration with only two nodes
     */
    /*@Bean
    public RedisClusterConfiguration  redisClusterConfiguration(){
        return new RedisClusterConfiguration()
                .clusterNode("192.168.1.1", 6379)
                .clusterNode("192.168.1.2", 6379);
    }*/

    /***
     * For Redis higher availablity, we use sentinel mode
     */
    /*
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
        return new RedisSentinelConfiguration()
                .master("redismaster")
                .sentinel("192.168.1.1", 6379)
                .sentinel("192.168.1.1", 6379);
    }
    */
}
