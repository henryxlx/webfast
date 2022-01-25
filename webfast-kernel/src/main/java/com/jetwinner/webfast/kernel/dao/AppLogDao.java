package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderByBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppLogDao {

    void insertMap(Map<String, Object> logModel);

    int searchCount(Map<String, Object> conditions);

    List<Map<String, Object>> searchList(Map<String, Object> conditions, OrderByBuilder orderByBuilder,
                                         Integer offset, Integer limit);
}
