package com.jetwinner.webfast.mvc.extension;

import com.jetwinner.servlet.RequestContextPathUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.mvc.extension.csrf.SessionCsrfProvider;
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

    protected WebExtensionPackAppServiceDelegator delegator;

    protected SessionCsrfProvider csrfProvider;

    public BaseWebExtensionPack(HttpServletRequest request, ApplicationContext appContext) {
        this.request = request;
        this.appContext = appContext;
        this.delegator = appContext.getBean(WebExtensionPackAppServiceDelegator.class);
        this.csrfProvider = SessionCsrfProvider.getDefaultInstance(request.getSession(true));
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String renderCsrfToken(String intention) {
        return this.csrfProvider.generateCsrfToken(intention);
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
        return delegator.appConst.getUploadPublicUrlPath();
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
        return delegator.appConst.getUploadPrivateDirectory();
    }

    protected String getUploadPublicDirectory(){
        return delegator.appConst.getUploadPublicDirectory();
    }

    public String getSetting(String name) {
        return getSetting(name, null);
    }
    public String getSetting(String name, String defaultValue) {
        String result = delegator.settingService.getSettingValue(name, defaultValue);
        if (result == null) {
            result = appContext.getEnvironment().getProperty(name);
        }
        return result;
    }
}
