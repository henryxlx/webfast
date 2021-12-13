package com.jetwinner.webfast;

import com.jetwinner.spring.SpringBootAppContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xulixin
 */
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class FastDemoApplication extends SpringBootServletInitializer {

    static Logger log = LoggerFactory.getLogger(FastDemoApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication app = new SpringApplication(FastDemoApplication.class);
            ConfigurableApplicationContext ctx = app.run(args);
            SpringBootAppContextHandler.me().setSpringBootAppPrimaryClass(FastDemoApplication.class)
                    .setArgs(args).setApplicationContext(ctx);
        } catch (Exception e) {
            log.error("DemoApplication startup error: ", e);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        this.setRegisterErrorPageFilter(false);
        return application.sources(FastDemoApplication.class);
    }

}