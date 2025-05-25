package com.yourssu.rookieton.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisInitService {

    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void initExpireTime() {
        redisTemplate.expire("USER_LOCATION", Duration.ofMinutes(10));
    }
}
