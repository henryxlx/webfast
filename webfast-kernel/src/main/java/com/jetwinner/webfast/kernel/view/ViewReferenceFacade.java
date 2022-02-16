package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import com.jetwinner.webfast.kernel.FastMenuHolder;
import org.springframework.context.ApplicationContext;

/**
 * @author xulixin
 */
public interface ViewReferenceFacade {

    FastAppConst appConst();

    FastDataDictHolder dictHolder();

    UserAccessControlService userAcl();

    FastMenuHolder menuHolder();

    ApplicationContext appContext();
}
