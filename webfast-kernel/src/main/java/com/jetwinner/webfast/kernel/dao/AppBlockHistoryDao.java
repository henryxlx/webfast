package com.jetwinner.webfast.kernel.dao;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockHistoryDao {

    Map<String, Object> getLatestBlockHistory();
}
