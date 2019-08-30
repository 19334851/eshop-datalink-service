package com.roncoo.eshop.datalink.service;

public interface CacheService {

    /**
     * 将商品信息保存到本地缓存中
     * @param productInfo
     * @return
     */
    public String saveLocalCache(Long id,String productInfo);

    /**
     * 从本地缓存中获取商品信息
     * @param id
     * @return
     */
    public String getLocalCache(Long id);
}
