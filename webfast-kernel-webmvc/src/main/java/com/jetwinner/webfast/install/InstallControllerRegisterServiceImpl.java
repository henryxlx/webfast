package com.jetwinner.webfast.install;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author xulixin
 */
@Component
public class InstallControllerRegisterServiceImpl implements InstallControllerRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(InstallControllerRegisterServiceImpl.class);

    static final String INSTALL_CONTROLLER_BEAN_NAME = "webfastInstallController";

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final ApplicationContext applicationContext;

    public InstallControllerRegisterServiceImpl(RequestMappingHandlerMapping requestMappingHandlerMapping,
                                                ApplicationContext applicationContext) {

        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInstallControllerMapping() throws Exception {
        if (!applicationContext.containsBean(INSTALL_CONTROLLER_BEAN_NAME)) {
            registerInstallControllerBeanDefinition();
        }
        Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass()
                .getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping, INSTALL_CONTROLLER_BEAN_NAME);
    }

    private void registerInstallControllerBeanDefinition() throws ClassNotFoundException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        Class<?> myController = ClassUtils.getDefaultClassLoader().loadClass("com.jetwinner.webfast.mvc.controller.install.InstallController");
        // 这里通过builder直接生成了installController的definition，然后注册进去
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(myController);
        defaultListableBeanFactory.registerBeanDefinition(INSTALL_CONTROLLER_BEAN_NAME, beanDefinitionBuilder.getBeanDefinition());
    }

    @Override
    public void removeInstallControllerMapping() {
        Object controller = applicationContext.getBean(INSTALL_CONTROLLER_BEAN_NAME);
        if (controller == null) {
            return;
        }
        Class<?> targetClass = controller.getClass();
        ReflectionUtils.doWithMethods(targetClass, method -> {
            Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
            try {
                Method createMappingMethod = RequestMappingHandlerMapping.class.
                        getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                createMappingMethod.setAccessible(true);
                RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                        createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                if (requestMappingInfo != null) {
                    requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                }
            } catch (Exception e) {
                logger.error("Removing InstallController request mapping error: " +  e.getMessage());
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
    }
}
