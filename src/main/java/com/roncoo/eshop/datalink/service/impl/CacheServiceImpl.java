package com.roncoo.eshop.datalink.service.impl;

import com.roncoo.eshop.datalink.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements CacheService {

    public static final String CACHE_NAME = "local";

    @CachePut(value = CACHE_NAME,key = "'key_'+#id")
    public String saveLocalCache(Long id,String productInfo) {
        return null;
    }


    @Cacheable(value = CACHE_NAME,key = "'key_'+#id")
    public String getLocalCache(Long id) {
        return null;
    }
}
