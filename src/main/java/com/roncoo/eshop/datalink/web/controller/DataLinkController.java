package com.roncoo.eshop.datalink.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.datalink.model.Diminfo;
import com.roncoo.eshop.datalink.rebuild.RebuildCacheQueue;
import com.roncoo.eshop.datalink.service.CacheService;
import com.roncoo.eshop.datalink.service.EshopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class DataLinkController {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private EshopProductService eshopProductService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getProduct")
    public String getProduct(Long productId) {
        /*
        System.out.println("getProduct" + productId);
        //String productData1 = eshopProductService.findProductById(productId);
        String productData1 = restTemplate.getForObject("http://eshop-product-service/product/findById?id=1", String.class);
        return productData1;
        */

        // 先读本地的ehcache，但是我们这里就不做了，因为之前都演示过了，大家自己做就可以了
        JSONObject dimInfo = cacheService.getLocalCache(productId);
        if(dimInfo != null){
            return dimInfo.toJSONString();
        }
        // 读redis主集群
        Jedis jedis = jedisPool.getResource();
        String dimProductJSON = jedis.get("dim_product_" + productId);
        if(dimProductJSON == null || "".equals(dimProductJSON)) {
            String productDataJSON = eshopProductService.findProductById(productId);
            if(productDataJSON != null && !"".equals(productDataJSON)) {
                JSONObject productDataJSONObject = JSONObject.parseObject(productDataJSON);
                String productPropertyDataJSON = eshopProductService.findProductPropertyByProductId(productId);
                if(productPropertyDataJSON != null && !"".equals(productPropertyDataJSON)) {
                    productDataJSONObject.put("product_property", JSONObject.parse(productPropertyDataJSON));
                }

                String productSpecificationDataJSON = eshopProductService.findProductSpecificationByProductId(productId);
                if(productSpecificationDataJSON != null && !"".equals(productSpecificationDataJSON)) {
                    productDataJSONObject.put("product_specification", JSONObject.parse(productSpecificationDataJSON));
                }
                try {
                    productDataJSONObject.put("modified_time", sdf.format(new Date()));
                }catch (Exception e) {
                    e.printStackTrace();
                }
                String productData = productDataJSONObject.toJSONString();

                RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
                rebuildCacheQueue.putDimInfo(productDataJSONObject);
                return productData;
            }else{
                return "";
            }
        }
        return dimProductJSON;

    }
}
