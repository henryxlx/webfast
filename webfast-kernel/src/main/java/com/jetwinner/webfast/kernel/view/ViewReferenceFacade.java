package com.jetwinner.webfast.kernel.view;

import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.DataDictHolder;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import org.springframework.context.ApplicationContext;

/**
 * @author xulixin
 */
public interface ViewReferenceFacade {

    AppWorkingConstant appConst();

    DataDictHolder dictHolder();

    UserAccessControlService userAcl();

    ApplicationContext appContext();
}
