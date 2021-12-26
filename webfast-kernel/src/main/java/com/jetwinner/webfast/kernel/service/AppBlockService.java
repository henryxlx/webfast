package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;

import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockService {

    Integer createBlock(Map<String, Object> model, BaseAppUser currentUser);

    void updateContent(Integer id, String content);
}
