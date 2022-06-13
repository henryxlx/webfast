package com.jetwinner.webfast.kernel.service;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppSettingService {

    void set(String name, Map<String, Object> mapForValue);

    Map<String, Object> get(String name);

    String getSettingValue(String key, String defaultValue);

    String getSettingValue(String key);
}
