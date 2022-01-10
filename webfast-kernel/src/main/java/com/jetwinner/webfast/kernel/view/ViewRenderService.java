package com.jetwinner.webfast.kernel.view;

import java.util.Map;

/**
 * @author xulixin
 */
public interface ViewRenderService {

    public String renderView(String viewLocation, Map<String, Object> model);

    public String renderView(String viewLocation, Map<String, Object> model, Map<String, Object> importMacroModel);
}
