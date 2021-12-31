package com.jetwinner.webfast.kernel.typedef;

import com.jetwinner.util.EasyStringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xulixin@x230-think-joomla
 * @date 2015/7/1
 */
public class ParamMap {

    private Map<String, Object> map = new HashMap<String, Object>();

    public static Map<String, Object> toMap(Map<String, String[]> map) {
        Map<String, Object> targetMap = new HashMap<>(map != null ? map.size() : 0);
        if (map != null) {
            map.forEach((k, v) -> {
                if (v != null && v.length > 0 && EasyStringUtil.isNotBlank(v[0])) {
                    targetMap.put(k, v[0]);
                }
            });
        }
        return targetMap;
    }

    public ParamMap add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return this.map;
    }

}