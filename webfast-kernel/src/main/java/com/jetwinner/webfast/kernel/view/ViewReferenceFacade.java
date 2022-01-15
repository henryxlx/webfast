package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.DataDictHolder;
import com.jetwinner.webfast.kernel.FastAppConst;
import org.springframework.context.ApplicationContext;

/**
 * @author xulixin
 */
public interface ViewReferenceFacade {

    FastAppConst appConst();

    DataDictHolder dictHolder();

    UserAccessControlService userAcl();

    ApplicationContext appContext();
}
