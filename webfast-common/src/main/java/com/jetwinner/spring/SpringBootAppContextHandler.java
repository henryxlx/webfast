package com.jetwinner.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author xulixin
 *
 * SpringBootAppContextHandler.me().setSpringBootAppPrimaryClass(SpringBootApplication.class)
 *                 .setArgs(args).setApplicationContext(ctx);
 */
public class SpringBootAppContextHandler {

    private static final SpringBootAppContextHandler INSTANCE = new SpringBootAppContextHandler();

    public static SpringBootAppContextHandler me() {
        return INSTANCE;
    }

    private SpringBootAppContextHandler() {
        // reserved.
    }

    private String[] args;
    private Class<?> springBootAppPrimaryClass;
    private ConfigurableApplicationContext applicationContext;

    public SpringBootAppContextHandler setArgs(String[] args) {
        this.args = args;
        return this;
    }

    public SpringBootAppContextHandler setSpringBootAppPrimaryClass(Class<?> primaryClass) {
        springBootAppPrimaryClass = primaryClass;
        return this;
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void reboot() {
        Assert.notNull(springBootAppPrimaryClass, "Spring Boot Application Primary Class must not be null!");
        Assert.notNull(args, "Spring Boot Application arguments must not be null!");

        ExecutorService threadPool = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        threadPool.execute(() -> {
            applicationContext.close();
            applicationContext = SpringApplication.run(springBootAppPrimaryClass, args);
        });
        threadPool.shutdown();
    }

    public <T> T getBean(Class<T> clazz) {
        return this.applicationContext.getBean(clazz);
    }

}
