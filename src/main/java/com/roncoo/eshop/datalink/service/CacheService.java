package com.roncoo.eshop.datalink.service;

import com.alibaba.fastjson.JSONObject;

public interface CacheService {

    /**
     * 将商品信息保存到本地缓存中
     * @param info
     * @return
     */
    public JSONObject saveLocalCache(Long id, JSONObject info);

    /**
     * 从本地缓存中获取商品信息
     * @param id
     * @return
     */
    public JSONObject getLocalCache(Long id);
}
