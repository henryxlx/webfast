package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.DataDictHolder;
import com.jetwinner.webfast.kernel.FastAppConst;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class ViewReferenceFacadeImpl implements ViewReferenceFacade {

    private final FastAppConst appConst;
    private final DataDictHolder dataDictHolder;
    private final UserAccessControlService userAccessControlService;
    private final ApplicationContext applicationContext;

    public ViewReferenceFacadeImpl(FastAppConst appConst,
                                   DataDictHolder dataDictHolder,
                                   UserAccessControlService userAccessControlService,
                                   ApplicationContext applicationContext) {

        this.appConst = appConst;
        this.dataDictHolder = dataDictHolder;
        this.userAccessControlService = userAccessControlService;
        this.applicationContext = applicationContext;
    }

    @Override
    public FastAppConst appConst() {
        return this.appConst;
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
