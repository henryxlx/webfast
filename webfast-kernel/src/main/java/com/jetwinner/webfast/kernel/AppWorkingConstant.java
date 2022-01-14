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

    /**
     * 应用程序使用外部存储位置路径
     */
    @Value("${custom.app.storage.path:d:/webfast/storage}")
    private String storagePath;

    /**
     * 单个文件的最大上限
     */
    @Value("${spring.servlet.multipart.max-file-size}")
    private String uploadMaxFilesize;

    /**
     * 单个请求的文件总大小上限
     */
    @Value("${spring.servlet.multipart.max-request-size}")
    private String postMaxsize;

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

    public String getUploadMaxFilesize() {
        return uploadMaxFilesize;
    }

    public String getPostMaxsize() {
        return postMaxsize;
    }
}
