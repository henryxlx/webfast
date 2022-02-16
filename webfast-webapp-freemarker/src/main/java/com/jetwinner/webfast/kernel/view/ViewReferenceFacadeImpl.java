package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import com.jetwinner.webfast.kernel.FastMenuHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class ViewReferenceFacadeImpl implements ViewReferenceFacade {

    private final FastAppConst appConst;
    private final FastDataDictHolder dataDictHolder;
    private final UserAccessControlService userAccessControlService;
    private final FastMenuHolder menuHolder;
    private final ApplicationContext applicationContext;

    public ViewReferenceFacadeImpl(FastAppConst appConst,
                                   FastDataDictHolder dataDictHolder,
                                   UserAccessControlService userAccessControlService,
                                   FastMenuHolder menuHolder,
                                   ApplicationContext applicationContext) {

        this.appConst = appConst;
        this.dataDictHolder = dataDictHolder;
        this.userAccessControlService = userAccessControlService;
        this.menuHolder = menuHolder;
        this.applicationContext = applicationContext;
    }

    @Override
    public FastAppConst appConst() {
        return this.appConst;
    }

    @Override
    public FastDataDictHolder dictHolder() {
        return this.dataDictHolder;
    }

    @Override
    public UserAccessControlService userAcl() {
        return this.userAccessControlService;
    }

    @Override
    public FastMenuHolder menuHolder() {
        return this.menuHolder;
    }

    @Override
    public ApplicationContext appContext() {
        return this.applicationContext;
    }
}
