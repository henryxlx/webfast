package com.jetwinner.webfast.kernel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class AppWorkingConstant {

    public static final String CHARSET_UTF8 = "UTF-8";

    @Value("${custom.app.debug:false}")
    private String debug;

    @Value("${custom.app.version:5.3.3}")
    private String version;

    @Value("${custom.app.storage.path:d:/webfast/storage}")
    private String storagePath;

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
