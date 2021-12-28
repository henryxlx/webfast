package com.jetwinner.webfast.mvc;

import com.jetwinner.servlet.RequestContextPathUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.MapUtil;
import com.jetwinner.webfast.DataDictHolder;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author xulixin
 */
public class WebExtensionPack extends BaseWebExtensionPack {

    public static final String MODEL_VAR_NAME = "webExtPack";

    private final DataDictHolder dataDictHolder;

    public WebExtensionPack(HttpServletRequest request,
                            ApplicationContext appContext,
                            DataDictHolder dataDictHolder) {

        super(request, appContext);
        this.dataDictHolder = dataDictHolder;
    }

    public String getDefaultPath(String category, String uri, String size, boolean absolute) {
        String url = "";
        if (EasyStringUtil.isBlank(uri)) {
            String publicUrlpath = "assets/img/default/";
            url = getUrl(publicUrlpath, size, category);

            // AppSettingService settingService = appContext.getBean(AppSettingService.class);
            // Map<String, ?> defaultSetting = settingService.get("default");
            Map<String, ?> defaultSetting = null;

            String key = "default" + upperCaseFirst(category);
            String fileName = key + "FileName";
            if (MapUtil.keyExists(key, defaultSetting) && MapUtil.keyExists(fileName, defaultSetting)) {
                if ("1".equals(defaultSetting.get(key))) {
                    url = getUrl(publicUrlpath, size, defaultSetting.get(fileName).toString());
                }
            }

            if (absolute) {
                url = RequestContextPathUtil.getSchemeAndHost(request) + url;
            }

            return url;
        }

        return parseUri(uri, absolute);
    }

    private String parseUri(String uri, Boolean absolute) {
        String url = "";

        int pos = uri.indexOf("://");
        if (pos >= 0) {
            url = uri.substring(pos);
        } else {
            url = uri;
        }

        String uploadPath = appContext.getEnvironment().getProperty("webfast.app.upload.public_url_path");
        if (uploadPath != null && !uploadPath.endsWith("/")) {
            uploadPath = uploadPath + "/";
            url = uploadPath + url;
        }

        url = getUrl(url);

        if (absolute) {
            url = RequestContextPathUtil.getSchemeAndHost(request) + url;
        }

        return url;
    }

    private String getUrl(String... uri) {
        StringBuilder sb = new StringBuilder();
        sb.append(RequestContextPathUtil.getContextPath(request));
        for (String s : uri) {
            sb.append(s);
        }
        return sb.toString();
    }

    private String upperCaseFirst(String s) {
        char[] cs = s.toCharArray();
        if (cs[0] >= 97 && cs[0] <= 122) {
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return s;
    }

    public String getDictText(String type, String key) {
        return dataDictHolder.text(type, key);
    }

}
