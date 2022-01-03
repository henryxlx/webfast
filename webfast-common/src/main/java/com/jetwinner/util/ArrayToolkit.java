package com.jetwinner.util;

import java.util.*;

/**
 * @author xulixin
 */
public final class ArrayToolkit {

    private ArrayToolkit() {
        // reserved.
    }

    public static Map<String, Map<String, Object>> index(List<Map<String, Object>> list, final String keyFor) {
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

    public static Map<String, Object> filterRequestMap(Map<String, String[]> parameterMap, String... parameterNames) {
        int len = parameterNames != null ? parameterNames.length : 0;
        Map<String, Object> map = new HashMap<>(len);
        if (parameterNames != null) {
            for (String pName : parameterNames) {
                if (parameterMap.containsKey(pName)) {
                    String[] values = parameterMap.get(pName);
                    if (values != null && values.length > 0) {
                        map.put(pName, values[0]);
                    }
                }
            }
        }
        return map;
    }
}
