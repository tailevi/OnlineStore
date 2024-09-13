package com.example.OnlineStore.configurations;

import com.example.OnlineStore.models.Product;
import com.example.OnlineStore.models.Reviews;
import com.example.OnlineStore.models.ReviewsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisCommandFactory){
        RedisTemplate<String,?> template = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, List<ReviewsDTO>> reviewRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, List<ReviewsDTO>> template = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setConnectionFactory(jedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }
}
