package com.roncoo.eshop.datalink.spring;

import org.springframework.boot.system.ApplicationTemp;
import org.springframework.context.ApplicationContext;

public class SpringContext {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        SpringContext.applicationContext = applicationContext;
    }
}
