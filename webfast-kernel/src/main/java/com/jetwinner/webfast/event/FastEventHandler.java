package com.jetwinner.webfast.event;

import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public final class FastEventHandler {

    public static FastEventHandler getDefault() {
        return INSTANCE;
    }

    private static final FastEventHandler INSTANCE = new FastEventHandler();

    private final Map<String, EventSubscriber> listenerMap;

    private FastEventHandler() {
        this.listenerMap = new HashMap<>();
    }

    public void register(EventSubscriber subscriber) {
        Map<String, String> eventMap = subscriber.getSubscribedEvents();
        eventMap.forEach((k, v) -> listenerMap.put(k, subscriber));
    }

    public void unregister(EventSubscriber subscriber) {
        Map<String, String> eventMap = subscriber.getSubscribedEvents();
        eventMap.forEach((k, v) -> listenerMap.remove(k));
    }

    public void dispatchEvent(String eventName, ServiceEvent serviceEvent) {
        EventSubscriber subscriber = listenerMap.get(eventName);
        if (subscriber != null) {
            Map<String, String> eventMap = subscriber.getSubscribedEvents();
            String methodName = eventMap.get(eventName);
            Class<?> beanClass = subscriber.getClass();
            Method[] methods = beanClass.getDeclaredMethods();
            for (Method beanMethod : methods) {
                int parameterCount = beanMethod.getParameterCount();
                if (parameterCount == 1 && beanMethod.getName().equals(methodName)) {
                    try {
                        beanMethod.invoke(subscriber, serviceEvent);
                    } catch (Exception e) {
                        throw new RuntimeGoingException(e.getMessage());
                    }
                }
            }
        }
    }
}
