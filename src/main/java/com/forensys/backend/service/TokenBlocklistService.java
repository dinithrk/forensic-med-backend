package com.forensys.backend.service;

import com.forensys.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlocklistService {

    private final StringRedisTemplate redisTemplate;
    private final JwtService jwtService;

    public void blacklistToken(String token) {
        Date expirationDate = jwtService.extractExpiration(token);
        long timeToLive = expirationDate.getTime() - System.currentTimeMillis();

        if (timeToLive > 0) {
            redisTemplate.opsForValue().set(token, "blacklisted", java.time.Duration.ofMillis(timeToLive));
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
