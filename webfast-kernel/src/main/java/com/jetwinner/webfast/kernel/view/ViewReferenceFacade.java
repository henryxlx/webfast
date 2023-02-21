package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
public interface ViewReferenceFacade {

    UserAccessControlService userAcl();

    Map<String, ?> getViewSharedVariable(HttpServletRequest request);
}
