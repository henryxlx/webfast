package com.jetwinner.webfast;

import com.jetwinner.spring.YmlPropertySourceFactory;
import com.jetwinner.util.MapUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
@PropertySource(value = {"classpath:datadict.yml"}, factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "data")
public class DataDictHolder {

    Map<String, Map<String, String>> dict = new HashMap<>();

    public Map<String, Map<String, String>> getDict() {
        return dict;
    }

    public void setDict(Map<String, Map<String, String>> dict) {
        this.dict = dict;
    }

    public String text(String type, String key) {
        Map<String, String> dictEntryMap = dict.get(type);
        if (MapUtil.isEmpty(dictEntryMap)) {
            return "";
        }
        String value = dictEntryMap.get(key);
        return value == null ? "" : value;
    }
}
