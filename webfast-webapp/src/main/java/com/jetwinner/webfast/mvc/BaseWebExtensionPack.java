package com.jetwinner.webfast.mvc;

import com.jetwinner.servlet.RequestContextPathUtil;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
public abstract class BaseWebExtensionPack {

    protected HttpServletRequest request;

    protected ApplicationContext appContext;

    public BaseWebExtensionPack(HttpServletRequest request, ApplicationContext appContext) {
        this.request = request;
        this.appContext = appContext;
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

    public String getBasePath() {
        return RequestContextPathUtil.createBaseUrl(request);
    }

    public String getSetting(String name) {
        return getSetting(name, null);
    }
    public String getSetting(String name, Object defaultValue) {
        return defaultValue == null ? null : String.valueOf(defaultValue);
    }
}
