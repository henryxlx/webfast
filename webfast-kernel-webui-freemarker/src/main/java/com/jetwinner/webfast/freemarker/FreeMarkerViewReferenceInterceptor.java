package com.jetwinner.webfast.freemarker;

import com.jetwinner.security.BaseAppUser;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.kernel.view.ViewReferenceFacadeImpl;
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

    private final DataSourceConfig dataSourceConfig;
    private final ViewReferenceFacadeImpl viewRef;

    public FreeMarkerViewReferenceInterceptor(DataSourceConfig dataSourceConfig,
                                              ViewReferenceFacadeImpl viewRef) {

        this.dataSourceConfig = dataSourceConfig;
        this.viewRef = viewRef;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            if (this.dataSourceConfig.getDataSourceDisabled()) {
                response.sendRedirect(request.getContextPath() + INSTALL_PATH);
                return false;
            }
            if (viewRef.userAcl().isLoggedIn()) {
                request.setAttribute(BaseAppUser.MODEL_VAR_NAME, viewRef.userAcl().getCurrentUser());
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

        mav.addAllObjects(viewRef.getViewSharedVariable(request));

    }

}
