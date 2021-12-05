package com.jetwinner.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author henry [xulixin at jetwinner home]
 * @since 1.0
 */
public final class RequestContextPathUtil {

    private RequestContextPathUtil() {
        // reserved.
    }

    public static final String REQ_BASE_URL = "baseUrl";

    public static final String REQ_CONTEXT = "ctx";

    public static final String createBaseUrl(HttpServletRequest request) {
        return createBaseUrl(request, null);
    }

    // 获取当前Web应用程序下HTTP请求的URL，比如：http://localhost:8080/jetmambo。结尾没有斜杠(/）
    public static final String createBaseUrl(HttpServletRequest request, String contextPath) {
        String scheme = request.getScheme();
        String server = request.getServerName();
        int port = request.getServerPort();
        if (contextPath == null) {
            contextPath = request.getContextPath();
        }

        StringBuffer url = new StringBuffer();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        url.append(scheme);
        url.append("://");
        url.append(server);
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }

        if (contextPath == null || "".equals(contextPath.trim())) {
            // noops;
        } else {
            if (contextPath.length() > 1) {
                url.append(contextPath);
            }
        }

        return url.toString();
    }

    public static final String getSchemeAndHost(ServletRequest request) {
        String scheme = request.getScheme();
        String server = request.getServerName();
        int port = request.getServerPort();

        StringBuffer url = new StringBuffer();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        url.append(scheme);
        url.append("://");
        url.append(server);
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }

        return url.toString();
    }

    public static final String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path.endsWith("/") ? path : path + "/";
    }

}
