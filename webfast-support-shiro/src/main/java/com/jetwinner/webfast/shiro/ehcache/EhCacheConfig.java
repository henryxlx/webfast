package com.jetwinner.webfast.shiro.ehcache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

/**
 * @author xulixin
 */
@Configuration
@ConditionalOnClass(net.sf.ehcache.CacheManager.class)
public class EhCacheConfig {

    @Value("${custom.app.ehcache.cfg.path:/ehcache/spring-ehcache.xml}")
    private String ehcacheConfigFilePath;

    @Bean
    public EhCacheCacheManager jmxCacheManager(CacheManager cacheManager) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, true, true);
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean enCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(ehcacheConfigFilePath));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public org.apache.shiro.cache.CacheManager shiroCacheManager(EhCacheManagerFactoryBean enCacheManagerFactoryBean) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(enCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }
}
