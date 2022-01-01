package com.jetwinner.webfast.kernel.service;

import com.jetwinner.webfast.kernel.BaseAppUser;

import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public interface AppBlockService {

    Integer createBlock(Map<String, Object> model, BaseAppUser currentUser);

    void updateContent(Integer id, String content);

    int searchBlockCount();

    List<Map<String, Object>> searchBlocks(int start, int limit);

    Map<String, Object> getLatestBlockHistory();
}
