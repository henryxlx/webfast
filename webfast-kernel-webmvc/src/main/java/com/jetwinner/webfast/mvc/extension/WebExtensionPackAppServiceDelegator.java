package com.jetwinner.webfast.mvc.extension;

import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import com.jetwinner.webfast.kernel.datatag.AppDataFetcherHolder;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class WebExtensionPackAppServiceDelegator {

    FastAppConst appConst;
    AppSettingService settingService;
    FastDataDictHolder dataDictHolder;
    AppDataFetcherHolder dataFetcherHolder;

    public WebExtensionPackAppServiceDelegator(FastAppConst appConst,
                                               AppSettingService settingService,
                                               FastDataDictHolder dataDictHolder,
                                               AppDataFetcherHolder dataFetcherHolder) {

        this.appConst = appConst;
        this.settingService = settingService;
        this.dataDictHolder = dataDictHolder;
        this.dataFetcherHolder = dataFetcherHolder;
    }
}
