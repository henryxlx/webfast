package com.jetwinner.webfast.mvc.extension;

import com.jetwinner.toolbag.ConvertIpToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.util.ValueParser;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @author xulixin
 */
public class WebExtensionPack extends BaseWebExtensionPack {

    public static final String MODEL_VAR_NAME = "webExtPack";

    public WebExtensionPack(HttpServletRequest request,
                            ApplicationContext appContext) {

        super(request, appContext);
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
        Map<String, Object> cdn = delegator.settingService.get("cdn");
        String cdnUrl = EasyStringUtil.isBlank(cdn.get("enabled")) ? null :
                PhpStringUtil.rtrim(String.valueOf(cdn.get("url")), " \\/");

        String url = "";
        if (EasyStringUtil.isBlank(uri)) {
            String publicUrlPath = "assets/img/default/";
            url = getAssetUrl(publicUrlPath + size + category);

            Map<String, Object> defaultSetting = delegator.settingService.get("default");

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
        return delegator.dataDictHolder.text(type, key);
    }

    public String getConvertIp(String ipAddress) {
        if (EasyStringUtil.isNotBlank(ipAddress)) {
            String location = ConvertIpToolkit.convertIp(ipAddress);
            return "INNA".equals(location) ? "未知区域" : location;
        }
        return "";
    }

    public List<Map<String, Object>> getDataTag(String keyForDataFetcher, Map<String, Object> params) {
        return delegator.dataFetcherHolder.get(keyForDataFetcher).getData(params);
    }

    public String getSystemDefaultPath(String category, boolean systemDefault) {
        String url;
        String publicUrlPath = "assets/img/default/";

        Map<String, Object> defaultSetting = delegator.settingService.get("default");
        if(systemDefault && !defaultSetting.isEmpty()){
            String fileName = "default" + StringUtils.capitalize(category) + "FileName";
            if (EasyStringUtil.isNotBlank(defaultSetting.get(fileName))) {
                url = getAssetUrl("files/" + publicUrlPath + defaultSetting.get(fileName));
            } else {
                url = getAssetUrl(publicUrlPath + category);
            }
        } else {
            url = getAssetUrl(publicUrlPath + category);
        }

        return url;
    }

}
