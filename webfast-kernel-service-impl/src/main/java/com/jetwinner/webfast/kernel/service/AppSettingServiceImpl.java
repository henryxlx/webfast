package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.AppSettingDao;
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
    public void set(String name, Map<String, Object> mapForValue) {
        settingDao.updateSetting(name, mapForValue);
    }

    @Override
    @Cacheable(value = "settingCache", key = "#name")
    public Map<String, Object> get(String name) {
        return settingDao.getSettingValueByName(name);
    }
}
