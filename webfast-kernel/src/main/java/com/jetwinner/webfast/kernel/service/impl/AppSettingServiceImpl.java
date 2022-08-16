package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.webfast.kernel.dao.AppSettingDao;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppSettingServiceImpl implements AppSettingService {

    private static final String SETTING_CACHE_NAME = "webfastSettingCache";

    private final AppSettingDao settingDao;

    public AppSettingServiceImpl(AppSettingDao settingDao) {
        this.settingDao = settingDao;
    }

    @Override
    @CacheEvict(value = SETTING_CACHE_NAME, key = "#name")
    public void set(String name, Map<String, Object> mapForValue) {
        settingDao.updateSetting(name, mapForValue);
    }

    @Override
    @Cacheable(value = SETTING_CACHE_NAME, key = "#name")
    public Map<String, Object> get(String name) {
        return settingDao.getSettingValueByName(name);
    }

    @Override
    public String getSettingValue(String key, String defaultValue) {
        String result = defaultValue;
        String[] names = key != null ? key.split("\\.") : new String[0];
        if (names.length > 1) {
            Map<String, Object> settingMap = get(names[0]);
            if (settingMap != null) {
                Object val = settingMap.get(names[1]);
                result = val != null ? val.toString() : result;
            }
        }
        return result;
    }

    @Override
    public String getSettingValue(String key) {
        return getSettingValue(key, null);
    }
}
