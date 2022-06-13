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

    private final AppSettingDao settingDao;

    public AppSettingServiceImpl(AppSettingDao settingDao) {
        this.settingDao = settingDao;
    }

    @Override
    @CacheEvict(value = "settingCache", key = "#name")
    public void set(String name, Map<String, Object> mapForValue) {
        settingDao.updateSetting(name, mapForValue);
    }

    @Override
    @Cacheable(value = "settingCache", key = "#name")
    public Map<String, Object> get(String name) {
        return settingDao.getSettingValueByName(name);
    }

    @Override
    public String getSettingValue(String key, String defaultValue) {
        String result = defaultValue != null ? defaultValue : null;
        String[] names = key.split("\\.");
        if (names != null && names.length > 1) {
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
