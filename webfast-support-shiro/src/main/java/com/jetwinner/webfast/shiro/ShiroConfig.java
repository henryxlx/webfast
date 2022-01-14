package com.jetwinner.webfast.shiro;

import com.jetwinner.properties.LinkedHashMapProperties;
import com.jetwinner.security.RbacService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author xulixin
 */
@Configuration
public class ShiroConfig {

    Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    static final String DEFAULT_HASH_ALGORITHM_NAME = Sha256Hash.ALGORITHM_NAME;
    static final int DEFAULT_HASH_ITERATIONS = 1024;

    @Value("${webfast.shiro.filterChainMappingLocation:shiro-filter-chain-mapping.properties}")
    private String filterChainMappingLocation;

    /**
     * 设置用于匹配密码的CredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法，这里使用更安全的sha256算法
        credentialsMatcher.setHashAlgorithmName(DEFAULT_HASH_ALGORITHM_NAME);
        // 数据库存储的密码字段使用HEX还是BASE64方式加密，false是使用BASE64方式加密
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        // 散列迭代次数
        credentialsMatcher.setHashIterations(DEFAULT_HASH_ITERATIONS);
        return credentialsMatcher;
    }

    @Bean
    public UserRealm userRealm(RbacService rbacService,
                               HashedCredentialsMatcher credentialsMatcher) {

        UserRealm userRealm = new UserRealm(rbacService);
        // 配置使用哈希密码匹配
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    /**
     * 配置security并设置userRealm，避免报错：xxxx required a bean named 'authorizer' that could not be found.
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(mySessionManager());
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager mySessionManager(){
        DefaultWebSessionManager defaultSessionManager = new DefaultWebSessionManager();
        // 将sessionIdUrlRewritingEnabled属性设置成false
        defaultSessionManager.setSessionIdUrlRewritingEnabled(false);
        return defaultSessionManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /* Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
         * 配置拦截器,实现无权限返回401,而不是跳转到登录页
         * filters.put("authc", new FormLoginFilter());
         * 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面 */
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 拦截器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    private Map<String, String> getFilterChainDefinitionMap() {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            Properties properties = new LinkedHashMapProperties();
            PropertiesLoaderUtils.fillProperties(properties, new ClassPathResource(filterChainMappingLocation));
            Set<String> keys = properties.stringPropertyNames();
            for (String key : keys) {
                map.put(key, properties.getProperty(key));
            }
        } catch (IOException e) {
            logger.warn("Can not location Shiro filter chain path definition mapping file: " + filterChainMappingLocation);
        }
        return map;
    }

}
