package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.AppNavigationDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppNavigationServiceImpl implements AppNavigationService {

    private final AppNavigationDao navigationDao;

    public AppNavigationServiceImpl(AppNavigationDao navigationDao) {
        this.navigationDao = navigationDao;
    }

    @Override
    public void createNavigation(Map<String, Object> model) {
        long now = System.currentTimeMillis();
        model.put("createdTime", now);
        model.put("updateTime", now);
        model.put("sequence", navigationDao.getNavigationsCountByType(model.get("type").toString()) + 1);
        navigationDao.insert(model);
    }
}
