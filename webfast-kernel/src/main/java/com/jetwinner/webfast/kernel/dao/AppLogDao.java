package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppLogDao {

    void insertMap(Map<String, Object> logModel);

    int searchLogCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchLogs(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                         Integer start, Integer limit);
}
