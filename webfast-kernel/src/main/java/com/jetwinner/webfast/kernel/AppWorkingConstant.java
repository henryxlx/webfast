package com.jetwinner.webfast.kernel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class AppWorkingConstant {

    public static final String CHARSET_UTF8 = "UTF-8";

    @Value("${custom.app.debug}")
    private String debug;

    @Value("${custom.app.version}")
    private String version;

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
}
