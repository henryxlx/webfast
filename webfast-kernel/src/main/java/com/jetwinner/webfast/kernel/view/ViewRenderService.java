package com.jetwinner.webfast.kernel.view;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
public interface ViewRenderService {

    public String renderView(HttpServletRequest request, String viewLocation, Map<String, Object> model);

    public String renderView(HttpServletRequest request, String viewLocation, Map<String, Object> model, Map<String, Object> importMacroModel);
}
