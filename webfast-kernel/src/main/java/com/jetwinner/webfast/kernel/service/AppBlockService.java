package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.BaseAppUser;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockService {

    Integer createBlock(Map<String, Object> model, BaseAppUser currentUser);

    void updateContent(Integer id, String content);

    int searchBlockCount();

    List<Map<String, Object>> searchBlocks(int start, int limit);

    Map<String, Object> getLatestBlockHistory();

    Map<String, Object> getBlock(Object id);

    Map<String, Object> getBlockByCode(String code);

    int countBlockHistoryByBlockId(Object id);

    Object generateBlockTemplateItems(Map<String, Object> block);

    List<Map<String, Object>> findBlockHistoriesByBlockId(Object id, Integer start, Integer limit);

    Map<String, Object> getBlockHistory(Object id);

    Map<String, Object> updateBlock(AppUser currentUser, String blockId, Map<String, Object> fields);
}
