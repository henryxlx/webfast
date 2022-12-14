package com.jetwinner.webfast.kernel.service.impl;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.dao.AppBlockDao;
import com.jetwinner.webfast.kernel.dao.AppBlockHistoryDao;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppBlockService;
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

    @Override
    public Map<String, Object> getBlock(Object id) {
        return blockDao.getBlock(id);
    }

    @Override
    public Map<String, Object> getBlockByCode(String code) {
        return blockDao.getBlockByCode(code);
    }

    @Override
    public int countBlockHistoryByBlockId(Object blockId) {
        return blockHistoryDao.countByBlockId(blockId);
    }

    @Override
    public Object generateBlockTemplateItems(Map<String, Object> block) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findBlockHistoriesByBlockId(Object blockId, Integer start, Integer limit) {
        return blockHistoryDao.findByBlockId(blockId, start, limit);
    }

    @Override
    public Map<String, Object> getBlockHistory(Object id) {
        return blockHistoryDao.getBlockHistory(id);
    }

    @Override
    public Map<String, Object> updateBlock(AppUser currentUser, String blockId, Map<String, Object> fields) {
        Map<String, Object> block = blockDao.getBlock(blockId);

        if (block == null || block.size() == 0) {
            throw new RuntimeGoingException("此编辑区不存在，更新失败!");
        }
        fields.put("updateTime", System.currentTimeMillis());
        blockDao.updateBlock(blockId, fields);
        Map<String, Object> updatedBlock = blockDao.getBlock(blockId);

        Map<String, Object> blockHistoryInfo = new ParamMap()
                .add("blockId", updatedBlock.get("id"))
                .add("content", updatedBlock.get("content"))
                .add("templateData", updatedBlock.get("templateData"))
                .add("userId", currentUser.getId())
                .add("createdTime", System.currentTimeMillis())
                .toMap();
        blockHistoryDao.addBlockHistory(blockHistoryInfo);

        // logService.info('block', 'update', "更新编辑区#{$id}", array('content' => $updatedBlock['content']));
        return updatedBlock;
    }
}
