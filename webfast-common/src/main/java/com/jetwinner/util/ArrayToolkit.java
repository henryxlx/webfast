package com.jetwinner.util;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xulixin
 */
public final class ArrayToolkit {

    private ArrayToolkit() {
        // reserved.
    }

    public static Map<String, Object> toConditionMap(HttpServletRequest request) {
        Set<String> keySet = request.getParameterMap().keySet();
        Map<String, Object> map = new HashMap<>(keySet == null ? 0 : keySet.size());
        if (keySet != null) {
            for (String key : keySet) {
                String value = request.getParameter(key);
                if (EasyStringUtil.isNotBlank(value)) {
                    map.put(key, value);
                }
            }
        }
        return map;
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

    public static Map<String, Object> filter(Map<String, String[]> parameterMap, String... parameterNames) {
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
