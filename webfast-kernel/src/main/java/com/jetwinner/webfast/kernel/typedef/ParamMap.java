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

    private static final String[] EXCLUDE_PARAMETER_NAME_ARRAY = {"_csrf_token"};

    private static Set<String> getExcludeParameterNames(String... array) {
        Set<String> set = SetUtil.newHashSet(EXCLUDE_PARAMETER_NAME_ARRAY);
        if (array != null && array.length > 0) {
            Collections.addAll(set, array);
        }
        return set;
    }

    private static Object getValueFromRequest(String key, HttpServletRequest request) {
        String[] values = request.getParameterValues(key);
        if (values != null) {
            int len = values.length;
            if (len > 1) {
                return values;
            } else if (len == 1) {
                return values[0];
            }
        }
        return null;
    }

    public static Map<String, Object> toCustomFormDataMap(HttpServletRequest request, String... includeNames) {
        if (includeNames == null || includeNames.length < 1) {
            return toFormDataMap(request);
        }
        Map<String, Object> map = new HashMap<>(includeNames.length);
        for (String key : includeNames) {
            Object value = getValueFromRequest(key, request);
            if (value == null) {
                continue;
            }
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> toFormDataMap(HttpServletRequest request, String... excludeKeys) {
        Set<String> excludeParameterNames = getExcludeParameterNames(excludeKeys);
        Map<String, Object> map = new HashMap<>(request.getParameterMap().size());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            if (excludeParameterNames.contains(key)) {
                continue;
            }
            Object value = getValueFromRequest(key, request);
            if (value == null) {
                continue;
            }
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> toUpdateDataMap(HttpServletRequest request, Map<String, Object> oldMap) {
        int size = oldMap != null ? oldMap.size() : 0;
        Map<String, Object> map = new HashMap<>(size);
        Set<String> keys = size > 0 ? oldMap.keySet() : new HashSet<>(0);
        keys.forEach(key -> {
            String value = request.getParameter(key);
            Object objForOldValue = oldMap.get(key);
            String oldValue = objForOldValue == null ? null : String.valueOf(objForOldValue);
            if (value != null && !value.equals(oldValue)) {
                map.put(key, value);
            }
        });
        return map;
    }

    private static boolean containKey(String key, String[] array) {
        if (array == null) {
            return true;
        }
        for (String str : array) {
            if (str.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, Object> toConditionMap(HttpServletRequest request, String... includeKeys) {
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> keyList = Collections.list(parameterNames);
        Map<String, Object> map = new HashMap<>(keyList.size());
        keyList.forEach(key -> {
            String value = request.getParameter(key);
            if (containKey(key, includeKeys) && EasyStringUtil.isNotBlank(value)) {
                map.put(key, value);
            }
        });
        return map;
    }
}