package com.roncoo.eshop.datalink.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.datalink.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    public static final String CACHE_NAME = "local";

    @CachePut(value = CACHE_NAME,key = "'key_'+#id")
    public JSONObject saveLocalCache(Long id, JSONObject info) {
        return info;
    }


    @Cacheable(value = CACHE_NAME,key = "'key_'+#id")
    public JSONObject getLocalCache(Long id) {
        return null;
    }
}
