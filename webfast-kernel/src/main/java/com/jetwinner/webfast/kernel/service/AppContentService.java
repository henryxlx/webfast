package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppContentService {

    void createContent(Map<String, Object> model, BaseAppUser currentUser);

    List<Map<String, Object>> findAllContent();
}
