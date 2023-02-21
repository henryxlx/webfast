package com.jetwinner.webfast.kernel.view;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import com.jetwinner.webfast.kernel.FastMenuHolder;
import com.jetwinner.webfast.mvc.extension.WebExtensionPack;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public UserAccessControlService userAcl() {
        return this.userAccessControlService;
    }

    @Override
    public Map<String, ?> getViewSharedVariable(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(5);
        map.put(ViewReferenceKeyEnum.MenuHolder.getName(), this.menuHolder);
        map.put(ViewReferenceKeyEnum.AppConst.getName(), this.appConst);
        map.put(ViewReferenceKeyEnum.RequestContextPath.getName(), request.getContextPath());
        map.put(ViewReferenceKeyEnum.UserAcl.getName(), this.userAccessControlService);
        map.put(WebExtensionPack.MODEL_VAR_NAME, new WebExtensionPack(request, this.applicationContext));
        return map;
    }
}
