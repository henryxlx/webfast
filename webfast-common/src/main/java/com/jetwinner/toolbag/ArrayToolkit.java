package com.jetwinner.toolbag;

import com.jetwinner.util.EasyStringUtil;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author xulixin
 */
public final class ArrayToolkit {

    private ArrayToolkit() {
        // reserved.
    }

    public static Map<String, Map<String, Object>> index(List<Map<String, Object>> list, final String key) {
        int len = list != null ? list.size() : 0;
        final Map<String, Map<String, Object>> map = new HashMap<>(len);
        if (len > 0) {
            list.forEach(e -> map.put(String.valueOf(e.get(key)), e));
        }
        return map;
    }

    public static Set<Object> column(Collection<Map<String, Object>> mapList, String columnName) {
        Set<Object> columnValues = new HashSet<>();
        if (mapList != null && mapList.size() > 0) {
            mapList.forEach(v -> {
                if (EasyStringUtil.isNotBlank(v.get(columnName))) {
                    columnValues.add(v.get(columnName));
                }
            });
        }
        return columnValues;
    }

    public static void filter(Map<String, Object> map, Map<String, Object> specialValues) {
        filter(map, specialValues, false);
    }

    public static void filter(Map<String, Object> map, Map<String, Object> specialValues, boolean needAddSpecialValue) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            Object val = entry.getValue();
            if (!specialValues.containsKey(key) || val == null) {
                it.remove();
            }
        }
        if (needAddSpecialValue) {
            specialValues.forEach((k, v) -> {
                if (!map.containsKey(k)) {
                    map.put(k, v);
                }
            });
        }
    }

    public static Map<String, Object> part(Map<String, Object> map, String... keys) {
        Assert.notNull(map, "ArrayToolkit::part parameter map must not be null.");
        int size = keys != null ? keys.length : 0;
        Map<String, Object> newMap = new HashMap<>(map.size());
        if (size > 0) {
            for (String key : keys) {
                if (map.containsKey(key)) {
                    newMap.put(key, map.get(key));
                }
            }
        }
        return newMap;
    }

    public static boolean required(Map<String, Object> map, String... keys) {
        if (keys == null || keys.length < 1) {
            return false;
        }
        for (String key : keys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public static void filterNullEntry(Map<String, Object> conditions) {
        Iterator<Map.Entry<String, Object>> it = conditions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (entry.getValue() == null) {
                it.remove();
            }
        }
    }

    public static Map<String, List<Map<String, Object>>> group(List<Map<String, Object>> items, String key) {
        Map<String, List<Map<String, Object>>> grouped = new HashMap<>();
        items.forEach(item -> {
            grouped.computeIfAbsent(String.valueOf(item.get(key)), v -> new ArrayList<>()).add(item);
        });
        return grouped;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            Map map = ((Map) obj);
            Set keys = map.keySet();
            boolean keyNotString = true;
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (key instanceof String) {
                    keyNotString = false;
                    break;
                }
            }
            if (keyNotString) {
                Map<String, Object> toMap = new HashMap<>(map.size());
                it = keys.iterator();
                while (it.hasNext()) {
                    Object k = it.next();
                    toMap.put(String.valueOf(k), map.get(k));
                }
                return toMap;
            }
            return (Map<String, Object>) obj;
        } else {
            Map<String, Object> map = new HashMap<>(1);
            map.put("mass", obj);
            return map;
        }
    }
}
