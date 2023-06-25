package com.jetwinner.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public class EasyWebFormEditor {

    private static final String[] EXCLUDE_PARAMETER_NAME_ARRAY = {"_csrf_token"};

    public static EasyWebFormEditor createNamedFormEditor(String formName, String... fieldNames) {
        return new EasyWebFormEditor(formName, fieldNames);
    }

    public static EasyWebFormEditor createFormEditor(String... fieldNames) {
        return new EasyWebFormEditor(null, fieldNames);
    }

    public static Map<String, Object> toFormDataMap(HttpServletRequest request) {
        return toFormDataMap(request, null, null);
    }

    public static Map<String, Object> toFormDataMap(HttpServletRequest request, String formName) {
        return toFormDataMap(request, formName, null);
    }

    public static Map<String, Object> toFormDataMap(HttpServletRequest request, String... fieldNames) {
        return toFormDataMap(request, null, fieldNames);
    }

    public static Map<String, Object> toFormDataMap(HttpServletRequest request, String formName, String... fieldNames) {
        EasyWebFormEditor formEditor = new EasyWebFormEditor(formName, fieldNames);
        formEditor.bind(request);
        formEditor.sweepField();
        return formEditor.getData();
    }

    private String[] fieldNames;

    private String formName;

    private Map<String, Object> mapFormData;

    private EasyWebFormEditor() {
        // reserved.
    }

    private EasyWebFormEditor(String formName, String[] fieldNames) {
        this.formName = formName;
        this.fieldNames = fieldNames;
    }

    private boolean isExcludeField(String value) {
        for (String name : EXCLUDE_PARAMETER_NAME_ARRAY) {
            if (name.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static final String SUFFIX_BRACKET = "[]";

    private void copyValidItemToNewMap(Map<String, Object> map, String key, Object value) {
        if (!isExcludeField(key)) {
            if (key.endsWith(SUFFIX_BRACKET)) {
                map.put(key.replace(SUFFIX_BRACKET, ""), value);
            } else {
                map.put(key, value);
            }
        }
    }

    private void sweepField() {
        if (mapFormData != null && mapFormData.size() > 0) {
            Map<String, Object> map = new HashMap<>(mapFormData.size());
            mapFormData.forEach((k, v) -> copyValidItemToNewMap(map, k, v));
            mapFormData = map;
        }
    }

    private void put(Map<String, Object> mapForm, String key, String[] values) {
        if (values != null) {
            if (values.length == 1) {
                mapForm.put(key, values[0]);
            } else {
                mapForm.put(key, values);
            }
        }
    }

    public EasyWebFormEditor bind(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        this.mapFormData = new HashMap<>(parameterMap.size());
        if (this.fieldNames != null && this.fieldNames.length > 0) {
            for (String fieldName : this.fieldNames) {
                if (parameterMap.containsKey(fieldName)) {
                    put(this.mapFormData, fieldName, parameterMap.get(fieldName));
                } else {
                    if (this.formName != null) {
                        String fieldNameWithPrefix = String.format("%s[%s]", this.formName, fieldName);
                        if (parameterMap.containsKey(fieldNameWithPrefix)) {
                            put(this.mapFormData, fieldName, parameterMap.get(fieldNameWithPrefix));
                        }
                    }
                }
            } // end for
        } else {
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement();
                put(this.mapFormData, key, parameterMap.get(key));
            }
        }
        return this;
    }

    public Map<String, Object> getData() {
        return this.mapFormData;
    }
}
