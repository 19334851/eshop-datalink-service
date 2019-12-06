package com.roncoo.eshop.datalink.listener;

import com.roncoo.eshop.datalink.rebuild.RebuildCacheThread;
import com.roncoo.eshop.datalink.spring.SpringContext;
import com.roncoo.eshop.datalink.zk.ZooKeeperSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce){
        /*
        ServletContext sc = sce.getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
        SpringContext.setApplicationContext(context);

        ZooKeeperSession.init();
        new Thread(new RebuildCacheThread()).start();
        */
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
