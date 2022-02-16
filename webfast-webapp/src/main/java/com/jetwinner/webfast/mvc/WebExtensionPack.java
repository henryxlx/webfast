package com.jetwinner.webfast.mvc;

import com.jetwinner.toolbag.ConvertIpToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @author xulixin
 */
public class WebExtensionPack extends BaseWebExtensionPack {

    public static final String MODEL_VAR_NAME = "webExtPack";

    private final FastDataDictHolder dataDictHolder;

    public WebExtensionPack(HttpServletRequest request,
                            ApplicationContext appContext,
                            FastDataDictHolder dataDictHolder) {

        super(request, appContext);
        this.dataDictHolder = dataDictHolder;
    }

    /**
     *
     * @param uri 不允许为空
     * @param defaultPath 默认值是空串
     * @param absolute 默认值是false
     */
    public String getFilePath(String uri, String defaultPath, boolean absolute) {
        String url = "";
        if (EasyStringUtil.isBlank(uri)) {
            url = getAssetUrl("assets/img/default/" + defaultPath);
            // url = getBaseUrl() + "/assets/img/default/"  + default;
            if (absolute) {
                url = getSchemeAndHttpHost()  + url;
            }
            return url;
        }
        Map<String, String> uriMap = parseFileUri(uri);
        if ("public".equals(uriMap.get("access"))) {
            url = PhpStringUtil.rtrim(getUploadPublicUrlPath(), " /") + "/" + uriMap.get("path");
            url = PhpStringUtil.ltrim(url, " /");
            url = getAssetUrl(url);

            if (absolute) {
                url = getSchemeAndHttpHost() + url;
            }
        }
        return url;
    }

    /**
     *
     * @param category 不能为空
     * @param uri 默认值是空串
     * @param size 默认值是空串
     * @param absolute 默认值是false
     */
    public String getDefaultPath(String category, String uri, String size, boolean absolute) {
        Map<String, Object> cdn = settingService.get("cdn");
        String cdnUrl = EasyStringUtil.isBlank(cdn.get("enabled")) ? null :
                PhpStringUtil.rtrim(String.valueOf(cdn.get("url")), " \\/");

        String url = "";
        if (EasyStringUtil.isBlank(uri)) {
            String publicUrlPath = "assets/img/default/";
            url = getAssetUrl(publicUrlPath + size + category);

            Map<String, Object> defaultSetting = settingService.get("default");

            String key = "default" + PhpStringUtil.ucfirst(category);
            String fileName = key + "FileName";
            if (defaultSetting.containsKey(key) && defaultSetting.containsKey(fileName)) {
                if (ValueParser.parseInt(defaultSetting.get(key)) == 1) {
                    url = getAssetUrl(publicUrlPath  + size + defaultSetting.get(fileName));
                }
            }

            if (absolute) {
                url = getSchemeAndHttpHost() + url;
            }

            return url;
        }

        Map<String, String> uriMap = parseFileUri(uri);
        if ("public".equals(uriMap.get("access"))) {
            url = PhpStringUtil.rtrim(getUploadPublicUrlPath(), " /") + "/" + uriMap.get("path");
            url = PhpStringUtil.ltrim(url, " /");
            url = getAssetUrl(url);

            if (cdnUrl != null) {
                url = cdnUrl + url;
            } else {
                if (absolute) {
                    url = getSchemeAndHttpHost() + url;
                }
            }
        }
        return url;
    }

    /**
     *
     * @param uri 不允许为空
     * @param defaultPath 默认值是空串
     * @param absolute 默认值是false
     */
    public String getFileUrl(String uri, String defaultPath, boolean absolute) {
        String url;
        if (EasyStringUtil.isBlank(uri)) {
            url = getAssetUrl("assets/img/default/" + defaultPath);
            if (absolute) {
                url = getSchemeAndHttpHost() + url;
            }
            return url;
        }

        url = PhpStringUtil.rtrim(getUploadPublicUrlPath(), " /") + "/" + uri;
        url = PhpStringUtil.ltrim(url, " /");
        url = getAssetUrl(url);

        if (absolute) {
            url = getSchemeAndHttpHost() + url;
        }
        return url;
    }

    public String getDictText(String type, String key) {
        return dataDictHolder.text(type, key);
    }

    public String getConvertIp(String ipAddress) {
        if (EasyStringUtil.isNotBlank(ipAddress)) {
            String location = ConvertIpToolkit.convertIp(ipAddress);
            return "INNA".equals(location) ? "未知区域" : location;
        }
        return "";
    }

}
