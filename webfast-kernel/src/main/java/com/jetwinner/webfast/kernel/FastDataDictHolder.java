package com.jetwinner.webfast.kernel;

import com.jetwinner.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public class FastDataDictHolder {

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
