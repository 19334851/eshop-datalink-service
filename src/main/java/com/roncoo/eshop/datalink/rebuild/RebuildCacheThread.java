package com.roncoo.eshop.datalink.rebuild;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.datalink.service.CacheService;
import com.roncoo.eshop.datalink.spring.SpringContext;
import com.roncoo.eshop.datalink.zk.ZooKeeperSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RebuildCacheThread implements Runnable{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        JedisPool jedisPool = SpringContext.getApplicationContext().getBean(JedisPool.class);
        CacheService cacheService = (CacheService)SpringContext.getApplicationContext().getBean("cacheService");
        RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
        ZooKeeperSession zkSession = ZooKeeperSession.getInstance();
        while (true){
            JSONObject dimInfo = rebuildCacheQueue.takeDimInfo();
            Long productId = dimInfo.getLong("id");
            zkSession.acquireDistributedLock(productId);
            Jedis jedis = jedisPool.getResource();
            String existedDimProductJSON = jedis.get("dim_product_" + productId);
            if(existedDimProductJSON != null && !"".equals(existedDimProductJSON)) {
                JSONObject existedDimJSONObject = JSONObject.parseObject(existedDimProductJSON);
                // 比较当前数据的时间版本比已有数据的时间版本是新还是旧
                try {
                    Date date = sdf.parse(dimInfo.getString("modified_time"));
                    Date existedDate = sdf.parse(existedDimJSONObject.getString("modified_time"));

                    if(date.before(existedDate)) {
                        System.out.println("current date[" + dimInfo.getString("modified_time") + "] is before existed date[" + existedDimJSONObject.getString("modified_time")+ "]");
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("existed product info is null......");
            }


            jedis.set("dim_product_" + productId, dimInfo.toJSONString());
            cacheService.saveLocalCache(productId,dimInfo);
            zkSession.releaseDistributedLock(productId);
        }
    }
}
