package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockHistoryDao {

    Map<String, Object> getLatestBlockHistory();

    int countByBlockId(Object blockId);

    List<Map<String, Object>> findByBlockId(Object blockId, Integer start, Integer limit);

    Map<String, Object> getBlockHistory(Object id);
}
