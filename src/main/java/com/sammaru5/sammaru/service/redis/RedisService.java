package com.sammaru5.sammaru.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    // 데이터 넣기
    public void setValues(String key, String value){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value, Duration.ofMinutes(3)); // 3분 뒤 메모리에서 삭제. (리프레쉬 토큰 유효기간 3분)
    }
    // 데이터 가져오기
    public String getValues(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }
    // 데이터 삭제
    public void deleteValues(String key) {
        redisTemplate.delete(key.substring(7));
    }
}
