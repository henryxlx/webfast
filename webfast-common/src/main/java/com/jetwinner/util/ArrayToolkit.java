package com.jetwinner.util;

import java.util.*;

/**
 * @author xulixin
 */
public final class ArrayToolkit {

    private ArrayToolkit() {
        // reserved.
    }

    public static Map<String, Map<String, Object>> toMapForFreeMarkerView(List<Map<String, Object>> list, final String keyFor) {
        final Map<String, Map<String, Object>> map = new HashMap<>(list.size());
        list.forEach(e -> map.put(String.valueOf(e.get(keyFor)), e));
        return map;
    }

    public static Set<Object> column(List<Map<String, Object>> mapList, String fieldName) {
        Set<Object> columnValues = new HashSet<>();
        mapList.forEach(v -> {
            if (EasyStringUtil.isNotBlank(v.get(fieldName))) {
                columnValues.add(v.get(fieldName));
            }
        });
        return columnValues;
    }
}
