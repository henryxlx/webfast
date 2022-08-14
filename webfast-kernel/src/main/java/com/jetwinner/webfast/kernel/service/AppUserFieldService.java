package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserFieldService {

    List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled();

    int searchFieldCount(Map<String, Object> condition);

    List<Map<String, Object>> getAllFieldsOrderBySeq();

    int addUserField(Map<String, Object> field);

    Map<String, Object> getField(Object id);

    void dropField(Object id);

    void updateField(Integer id, Map<String, Object> field);
}
