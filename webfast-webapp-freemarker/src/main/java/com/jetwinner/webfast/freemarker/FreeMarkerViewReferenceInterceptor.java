package com.jetwinner.webfast.freemarker;

import com.jetwinner.webfast.DataDictHolder;
import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.BaseAppUser;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import com.jetwinner.webfast.mvc.WebExtensionPack;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xulixin
 */
@Component
public class FreeMarkerViewReferenceInterceptor implements HandlerInterceptor {

    private static final String INSTALL_PATH = "/install";

    private final AppWorkingConstant appWorkingConstant;
    private final DataSourceConfig dataSourceConfig;
    private final DataDictHolder dataDictHolder;
    private final UserAccessControlService userAccessControlService;
    private final ApplicationContext applicationContext;

    public FreeMarkerViewReferenceInterceptor(AppWorkingConstant appWorkingConstant,
                                              DataSourceConfig dataSourceConfig,
                                              DataDictHolder dataDictHolder,
                                              UserAccessControlService userAccessControlService,
                                              ApplicationContext applicationContext) {

        this.appWorkingConstant = appWorkingConstant;
        this.dataSourceConfig = dataSourceConfig;
        this.dataDictHolder = dataDictHolder;
        this.userAccessControlService = userAccessControlService;
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            if (this.dataSourceConfig.getDataSourceDisabled()) {
                response.sendRedirect(request.getContextPath() + INSTALL_PATH);
                return false;
            }
            if (userAccessControlService.isLoggedIn()) {
                request.setAttribute(BaseAppUser.MODEL_VAR_NAME, userAccessControlService.getCurrentUser());
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mav)
            throws Exception {

        if (mav == null || mav.wasCleared()) {
            return;
        }

        addObject("appConst", appWorkingConstant, request, mav);
        addObject("ctx", request.getContextPath(), request, mav);
        addObject("dict", dataDictHolder.getDict(), request, mav);
        addObject("userAcl", userAccessControlService, request, mav);
        addObject(WebExtensionPack.MODEL_VAR_NAME, new WebExtensionPack(request, applicationContext), request, mav);
    }

    private void addObject(String name, Object value, HttpServletRequest request, ModelAndView mav) {
        if (request.getAttribute(name) == null) {
            mav.addObject(name, value);
        }
    }
}
