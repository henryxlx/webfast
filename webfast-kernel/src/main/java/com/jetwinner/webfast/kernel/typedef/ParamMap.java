package com.jetwinner.webfast.kernel.typedef;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xulixin@x230-think-joomla
 * @date 2015/7/1
 */
public class ParamMap {

    private Map<String, Object> map = new HashMap<String, Object>();

    public ParamMap add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return this.map;
    }

}