package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.dao.AppBlockDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppBlockServiceImpl implements AppBlockService {

    private final AppBlockDao blockDao;

    public AppBlockServiceImpl(AppBlockDao blockDao) {
        this.blockDao = blockDao;
    }

    @Override
    public Integer createBlock(Map<String, Object> model, BaseAppUser currentUser) {
        if (!model.containsKey("code") || !model.containsKey("title")) {
            throw new RuntimeGoingException("创建编辑区失败，缺少必要的字段");
        }

        model.put("userId", currentUser.getId());
        if (model.get("tips") == null) {
            model.put("tips", "");
        }
        long now = System.currentTimeMillis();
        model.put("createdTime", now);
        model.put("updateTime", now);
        return blockDao.insert(model);
    }

    @Override
    public void updateContent(Integer id, String content) {

    }
}
