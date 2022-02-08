package com.jetwinner.webfast.kernel.service;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppTagService {

    List<Map<String, Object>> findTagsByNames(String[] names);
}
