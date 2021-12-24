package com.jetwinner.webfast.kernel.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppSettingDao {

    int deleteSettingByName(String name);

    void updateSetting(String name, Map<String, Object> mapForSettingValue);

    Map<String, Object> getSettingValueByName(String name);
}
