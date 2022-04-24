package com.sammaru5.sammaru.config;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class ArticleCacheKeyGenerator implements KeyGenerator {
    private static final String KEY_FORMAT = "article:";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String id = String.valueOf(params[0]);
        return KEY_FORMAT + id;
    }
}