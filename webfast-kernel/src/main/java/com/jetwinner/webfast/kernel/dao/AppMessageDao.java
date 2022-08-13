package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.model.AppModelMessage;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppMessageDao {

    AppModelMessage getMessage(int id);

    int searchMessagesCount(Map<String, Object> conditions);

    List<AppModelMessage> searchMessages(Map<String, Object> conditions, OrderBy orderBy,
                                         int start, int limit);

    int addMessage(Map<String, Object> fields);

    List<AppModelMessage> findMessagesByIds(List<Integer> ids);

    void deleteMessage(Integer id);
}
