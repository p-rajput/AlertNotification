package com.notification.service;

import com.notification.dto.CacheDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheService {

    private static CacheService instance;
    private static ConcurrentMap<String, CacheDto> cache = new ConcurrentHashMap<>();

    //Singleton design
    private CacheService() {
        // Private constructor to prevent instantiation
    }

    public static CacheService getInstance() {
        if (instance == null) {
            synchronized (CacheService.class) {
                if (instance == null) {
                    instance = new CacheService();
                }
            }
        }
        return instance;
    }

    public CacheDto getKey(String key) {
        if (Objects.isNull(cache) || !cache.containsKey(key)) {
            return null;
        }
        return cache.get(key);
    }

    public void putInCache(String key, CacheDto value) {
        if (Objects.isNull(cache)) {
            return;
        }
        cache.put(key, value);
    }
}
