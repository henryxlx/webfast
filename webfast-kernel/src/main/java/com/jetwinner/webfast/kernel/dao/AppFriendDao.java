package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulixin
 */
public interface AppFriendDao {

    List<Map<String, Object>> getFriendsByFromIdAndToIds(Integer fromId, Set<Object> toIds);

    Map<String, Object> getFriendByFromIdAndToId(Integer fromId, Integer toId);

    Map<String, Object> addFriend(Map<String, Object> fields);

    int deleteFriend(Object id);

    List<Map<String, Object>> findAllUserFollowingByFromId(Integer fromId);

    List<Map<String, Object>> findAllUserFollowerByToId(Integer toId);
}
