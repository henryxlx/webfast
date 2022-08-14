package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserFieldDao {

    List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled();

    int searchFieldCount(Map<String, Object> condition);

    List<Map<String, Object>> getAllFieldsOrderBySeq();

    int addField(Map<String, Object> field);

    Map<String, Object> getFieldByFieldName(String fieldName);

    Map<String, Object> getField(Object id);

    void deleteField(Object id);

    void updateField(Integer id, Map<String, Object> field);
}
