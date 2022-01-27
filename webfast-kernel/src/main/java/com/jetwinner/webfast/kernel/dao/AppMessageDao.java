package com.jetwinner.webfast.kernel.dao;

import com.jetwinner.webfast.kernel.model.AppModelMessage;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppMessageDao {

    AppModelMessage addMessage(Map<String, Object> entityMap);
}
