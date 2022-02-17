package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.webfast.kernel.dao.AppUserFieldDao;
import com.jetwinner.webfast.kernel.service.AppUserFieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppUserFieldServiceImpl implements AppUserFieldService {

    private final AppUserFieldDao userFieldDao;

    public AppUserFieldServiceImpl(AppUserFieldDao userFieldDao) {
        this.userFieldDao = userFieldDao;
    }

    @Override
    public List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled() {
        return userFieldDao.getAllFieldsOrderBySeqAndEnabled();
    }
}
