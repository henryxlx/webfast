package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppUserFieldService {

    List<Map<String, Object>> getAllFieldsOrderBySeqAndEnabled();
}
