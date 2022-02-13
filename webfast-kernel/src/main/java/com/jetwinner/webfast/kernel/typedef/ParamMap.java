package com.jetwinner.webfast.kernel.typedef;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.SetUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * @author xulixin@x230-think-joomla
 * @date 2015/7/1
 */
public class ParamMap {

    private final Map<String, Object> map = new HashMap<>();

    public ParamMap add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return this.map;
    }

    public static Map<String, Object> toNewHashMap(int size) {
        return new HashMap<>(size);
    }

    private static final String[] EXCLUDE_PARAMETER_NAME_ARRAY = {"_csrf_token"};

    private static Set<String> getExcludeParameterNames(String... array) {
        Set<String> set = SetUtil.newHashSet(EXCLUDE_PARAMETER_NAME_ARRAY);
        if (array != null && array.length > 0) {
            Collections.addAll(set, array);
        }
        return set;
    }

    public static Map<String, Object> toFilterPostDataMap(HttpServletRequest request, String... includeNames) {
        if (includeNames == null || includeNames.length < 1) {
            return toPostDataMap(request);
        }
        Map<String, Object> map = new HashMap<>(includeNames.length);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            boolean containsName = false;
            for (String name : includeNames) {
                if (key.equals(name)) {
                    containsName = true;
                    break;
                }
            }
            if (containsName) {
                map.put(key, request.getParameter(key));
            }
        }
        return map;
    }

    public static Map<String, Object> toPostDataMap(HttpServletRequest request, String... excludeKeys) {
        Set<String> excludeParameterNames = getExcludeParameterNames(excludeKeys);
        Map<String, Object> map = new HashMap<>(request.getParameterMap().size());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            if (excludeParameterNames.contains(key)) {
                continue;
            }
            String value = request.getParameter(key);
            if (EasyStringUtil.isBlank(value)) {
                continue;
            }
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> toUpdateDataMap(Map<String, String[]> parameterMap, Map<String, Object> oldMap) {
        Map<String, Object> map = new HashMap<>(0);
        if (parameterMap != null) {
            for (String key : parameterMap.keySet()) {
                if (!oldMap.containsKey(key)) {
                    continue;
                }
                String[] values = parameterMap.get(key);
                if (EasyStringUtil.isNotBlank(values[0])) {
                    Object oldValue = oldMap.get(key);
                    if (!values[0].equals(oldValue)) {
                        map.put(key, values[0]);
                    }
                }
            }
        }
        return map;
    }

    public static Map<String, Object> toConditionMap(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> keyList = Collections.list(parameterNames);
        Map<String, Object> map = new HashMap<>(keyList.size());
        for (String key : keyList) {
            String value = request.getParameter(key);
            if (EasyStringUtil.isNotBlank(value)) {
                map.put(key, value);
            }
        }
        return map;
    }

    public static Map<String, Object> toConditionMap(Map<String, String[]> sourceMap) {
        Map<String, Object> toMap = new HashMap<>(sourceMap != null ? sourceMap.size() : 0);
        if (sourceMap != null) {
            sourceMap.forEach((k, v) -> {
                if (v != null && v.length > 0 && EasyStringUtil.isNotBlank(v[0])) {
                    toMap.put(k, v[0]);
                }
            });
        }
        return toMap;
    }

    public static Map<String, Object> filterConditionMap(Map<String, Object> conditions, String... includeKeys) {
        Map<String, Object> map = new HashMap<>(includeKeys != null ? includeKeys.length : 0);
        if (includeKeys != null) {
            for (String key : includeKeys) {
                Object value = conditions.get(key);
                if (EasyStringUtil.isNotBlank(value)) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }

}