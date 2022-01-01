package com.jetwinner.webfast.kernel.service;

import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.dao.AppBlockDao;
import com.jetwinner.webfast.kernel.dao.AppBlockHistoryDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class AppBlockServiceImpl implements AppBlockService {

    private final AppBlockDao blockDao;
    private final AppBlockHistoryDao blockHistoryDao;

    public AppBlockServiceImpl(AppBlockDao blockDao, AppBlockHistoryDao blockHistoryDao) {
        this.blockDao = blockDao;
        this.blockHistoryDao = blockHistoryDao;
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
        Map<String, Object> block = blockDao.getBlock(id);
        if (MapUtil.isEmpty(block)) {
            throw new RuntimeGoingException("此编辑区不存在，更新失败!");
        }

        blockDao.updateBlock(id, new ParamMap().add("content", content).toMap());
    }

    @Override
    public int searchBlockCount() {
        return blockDao.searchBlockCount();
    }

    @Override
    public List<Map<String, Object>> searchBlocks(int start, int limit) {
        return blockDao.findBlocks(start, limit);
    }

    @Override
    public Map<String, Object> getLatestBlockHistory() {
        return blockHistoryDao.getLatestBlockHistory();
    }
}
