package com.jetwinner.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public final class ArrayTookKit {

    private ArrayTookKit() {
        // reserved.
    }

    public static Map<String, Map<String, Object>> toMapForFreeMarkerView(List<Map<String, Object>> list, final String keyFor) {
        final Map<String, Map<String, Object>> map = new HashMap<>(list.size());
        list.forEach(e -> map.put(String.valueOf(e.get(keyFor)), e));
        return map;
    }
}
