package com.jetwinner.webfast.kernel.service;

import java.util.Map;

/**
 * @author xulixin
 */
public interface ViewRenderService {

    public String renderView(String viewLocation, Map<String, Object> model);
}
