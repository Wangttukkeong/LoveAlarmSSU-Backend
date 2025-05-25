package com.yourssu.rookieton.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisInitService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.data.redis.geo-key}")
    private String geoKey;

    @PostConstruct
    public void initExpireTime() {
        redisTemplate.expire(geoKey, Duration.ofMinutes(10));
    }
}
