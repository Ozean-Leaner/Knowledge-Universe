package com.ozean.ku.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void set(String key, String value);

    void set(String key, String value, long expireTime, TimeUnit timeUnit);

    Object get(String key);

    boolean delete(String key);

    boolean hasKey(String key);

    boolean expire(String key, long expireTime, TimeUnit timeUnit);

}
