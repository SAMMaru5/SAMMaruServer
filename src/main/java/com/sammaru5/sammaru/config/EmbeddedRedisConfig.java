package com.sammaru5.sammaru.config;

import com.sammaru5.sammaru.util.ArticleCacheKeyGenerator;
import com.sammaru5.sammaru.util.CacheKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.embedded.RedisServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Profile("!prod")
@Configuration
@EnableRedisRepositories
@EnableCaching
public class EmbeddedRedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        // 여러 @SpringBootTest를 실행하다보면 이미 redisServer가 실행중인데, redisServer.start()를 실행하면서 포트 충돌이 발생하는 경우가 생긴다.
        // 하지만 이때, 다른 @SpringBootTest에서 생성한 redisServer를 직접적으로 조작할 수는 없어도 통신하는 것은 가능하기 때문에 생성에 실패하더라도 테스트를 수행하는데는 문제가 없다.
        try {
            redisServer.start();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 아래 처럼 Serializer를 하지 않으면, key값이 알아볼 수 없게 조회된다.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(3L)); //캐시 유효기간 3분


        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        //article cache
        cacheConfigurations.put(CacheKey.ARTICLE, RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(CacheKey.ARTICLE_EXPIRE_SEC)));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory()).cacheDefaults(redisCacheConfiguration).withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public KeyGenerator articleCacheKeyGenerator() {
        return new ArticleCacheKeyGenerator();
    }

}
