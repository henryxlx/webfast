package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockDao {

    Integer insert(Map<String, Object> model);

    int searchBlockCount();

    List<Map<String, Object>> findBlocks(int start, int limit);

    Map<String, Object> getBlock(Object id);

    void updateBlock(Object id, Map<String, Object> fields);

    Map<String, Object> getBlockByCode(String code);
}
