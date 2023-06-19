package com.jetwinner.webfast.event;

import com.jetwinner.util.FastHashMap;
import com.jetwinner.webfast.kernel.AppUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public class ServiceEvent {

    private final AppUser currentUser;
    private final Map<String, Object> subject;
    private final Map<String, Object> arguments;

    public ServiceEvent(Map<String, Object> subject, Map<String, Object> arguments) {
        AppUser user = new AppUser();
        user.setId(1);
        this.currentUser = user;
        this.subject = subject;
        this.arguments = arguments;
    }

    public ServiceEvent(AppUser currentUser, Map<String, Object> subject) {
        this(currentUser, subject, new HashMap<>(0));
    }

    public ServiceEvent(AppUser currentUser, Map<String, Object> subject, Map<String, Object> arguments) {
        this.currentUser = currentUser;
        this.subject = subject;
        this.arguments = arguments;
    }

    public Map<String, Object> getSubject() {
        return this.subject;
    }

    public Object getArgument(String key) {
        return this.arguments != null ? this.arguments.get(key) : key;
    }

    public AppUser getCurrentUser() {
        return this.currentUser;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getArgumentAsMap(String key) {
        if (this.arguments == null) {
            return new HashMap<>(0);
        }
        Object val = this.arguments.get(key);
        if (val instanceof Map) {
            return (Map<String, Object>) val;
        } else {
            return FastHashMap.build(1).add(key, val).toMap();
        }
    }
}
