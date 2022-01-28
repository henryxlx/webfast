package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppFriendDao {

    List<Map<String, Object>> getFriendsByFromIdAndToIds(Integer fromId, Set<Object> toIds);
}
