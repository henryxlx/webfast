package com.jetwinner.webfast.mvc;

import com.jetwinner.servlet.RequestContextPathUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public abstract class BaseWebExtensionPack {

    protected HttpServletRequest request;

    protected ApplicationContext appContext;

    protected FastAppConst appConst;
    protected AppSettingService settingService;

    public BaseWebExtensionPack(HttpServletRequest request, ApplicationContext appContext) {
        this.request = request;
        this.appContext = appContext;
        this.appConst = appContext.getBean(FastAppConst.class);
        this.settingService = appContext.getBean(AppSettingService.class);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String csrfToken(String seed) {
        return "";
    }

    public String getSchemeAndHttpHost() {
        return RequestContextPathUtil.getSchemeAndHost(this.request);
    }

    public String getBaseUrl() {
        return RequestContextPathUtil.createBaseUrl(request);
    }

    String getAssetUrl(String... uri) {
        StringBuilder sb = new StringBuilder();
        sb.append(RequestContextPathUtil.getContextPath(request));
        for (String s : uri) {
            sb.append(s);
        }
        return sb.toString();
    }

    String getUploadPublicUrlPath() {
        return appConst.getUploadPublicUrlPath();
    }

    public Map<String, String> parseFileUri(String uri) {
        Map<String, String> parsed = new HashMap<>(5);
        String[] parts = uri.split("://");
        if (parts == null || parts.length != 2) {
            throw new RuntimeGoingException(String.format("解析文件URI(%s)失败！", uri));
        }
        parsed.put("access", parts[0]);
        parsed.put("path", parts[1]);
        parsed.put("directory", PhpStringUtil.dirname(parsed.get("path")));
        parsed.put("name", PhpStringUtil.basename(parsed.get("path")));

        String directory = getUploadPublicDirectory();

        if ("public".equals(parsed.get("access"))) {
            directory = getUploadPublicDirectory();
        } else {
            directory = getUploadPrivateDirectory();
        }
        parsed.put("fullpath", directory + "/" + parsed.get("path"));
        return parsed;
    }

    protected String getUploadPrivateDirectory() {
        return appConst.getUploadPrivateDirectory();
    }

    protected String getUploadPublicDirectory(){
        return appConst.getUploadPublicDirectory();
    }

    public String getSetting(String name) {
        return getSetting(name, null);
    }
    public String getSetting(String name, String defaultValue) {
        String result = defaultValue != null ? defaultValue : null;
        String[] names = name.split("\\.");
        if (names != null && names.length > 1) {
            Map<String, Object> settingMap = settingService.get(names[0]);
            if (settingMap != null) {
                Object val = settingMap.get(names[1]);
                result = val != null ? val.toString() : result;
            }
        }
        return result;
    }
}
