package com.jetwinner.webfast.kernel.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserFieldDao {

    List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled();
}
