package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.dao.AppTagDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppTagServiceImpl implements AppTagService {

    private final AppTagDao tagDao;

    public AppTagServiceImpl(AppTagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Map<String, Object>> findTagsByNames(String[] names) {
        return tagDao.findTagsByNames(names);
    }
}
