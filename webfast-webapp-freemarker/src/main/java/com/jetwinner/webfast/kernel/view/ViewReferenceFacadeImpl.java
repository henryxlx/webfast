package com.jetwinner.webfast.kernel.view;

import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.DataDictHolder;
import com.jetwinner.webfast.kernel.service.UserAccessControlService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class ViewReferenceFacadeImpl implements ViewReferenceFacade {

    private final AppWorkingConstant appWorkingConstant;
    private final DataDictHolder dataDictHolder;
    private final UserAccessControlService userAccessControlService;
    private final ApplicationContext applicationContext;

    public ViewReferenceFacadeImpl(AppWorkingConstant appWorkingConstant,
                                   DataDictHolder dataDictHolder,
                                   UserAccessControlService userAccessControlService,
                                   ApplicationContext applicationContext) {

        this.appWorkingConstant = appWorkingConstant;
        this.dataDictHolder = dataDictHolder;
        this.userAccessControlService = userAccessControlService;
        this.applicationContext = applicationContext;
    }

    @Override
    public AppWorkingConstant appConst() {
        return this.appWorkingConstant;
    }

    @Override
    public DataDictHolder dictHolder() {
        return this.dataDictHolder;
    }

    @Override
    public UserAccessControlService userAcl() {
        return this.userAccessControlService;
    }

    @Override
    public ApplicationContext appContext() {
        return this.applicationContext;
    }
}
