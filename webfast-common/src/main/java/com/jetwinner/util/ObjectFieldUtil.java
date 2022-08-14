package com.jetwinner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by x230-think-joomla on 2015/5/29.
 */
public abstract class ObjectFieldUtil {

    private static Logger logger = LoggerFactory.getLogger(ObjectFieldUtil.class);

    public static Object getFieldValueByName(String fieldName, Object obj) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(obj, new Object[]{});
            return value;
        } catch (Exception e) {
            // logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static Map<String, Object> getFieldNameHasValue(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                try {
                    Object value = getFieldValueByName(field.getName(), obj);
                    if (EasyStringUtil.isNotBlank(value)) {
                        map.put(field.getName(), value);
                    }
                } catch (Exception e) {
                     logger.error(e.getMessage(), e);
                }
            }
        }

        return map;
    }

    public static boolean isValidField(String key, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                try {
                    if (field.getName().equals(key)) {
                        return true;
                    }
                } catch (Exception e) {
                     logger.error(e.getMessage(), e);
                }
            }
        }
        return false;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        Field[] fields = obj.getClass().getDeclaredFields(); //Get all fields incl. private ones
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                String value;
                try {
                    value = (String) field.get(obj);
                } catch (ClassCastException e) {
                    value = "";
                }
                sb.append(key).append(": ").append(value).append("\n");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
