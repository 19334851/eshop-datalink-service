package com.roncoo.eshop.datalink.rebuild;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class RebuildCacheQueue {

    private ArrayBlockingQueue<JSONObject> queue = new ArrayBlockingQueue<>(1000);


    public void putDimInfo(JSONObject dimInfo){
        try {
            queue.put(dimInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject takeDimInfo(){

        try {
            return queue.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Singleton{
        private static RebuildCacheQueue instance;

        static {
            instance = new RebuildCacheQueue();
        }

        public static RebuildCacheQueue getInstance(){
            return instance;
        }

    }

    public static RebuildCacheQueue getInstance(){
        return Singleton.getInstance();
    }

    public static void init(){
        getInstance();
    }
}
