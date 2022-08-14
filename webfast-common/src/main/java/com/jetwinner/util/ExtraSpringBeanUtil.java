package com.jetwinner.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author xulixin
 */
public final class ExtraSpringBeanUtil extends BeanUtils {

    public ExtraSpringBeanUtil() {
        // reserved.
    }

    public static <T> T propertiesCopy(Object source, Class<T> clazz) {
        if (null == source) {
            return null;
        } else {
            try {
                T obj = clazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, obj);
                return obj;
            } catch (IllegalAccessException | InstantiationException |
                    NoSuchMethodException | InvocationTargetException ex) {

                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * list中对象的copy
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> collectionCopy(Collection source, Class<T> clazz) {
        if (null == source) {
            return new ArrayList<>();
        } else {
            List<T> list = new ArrayList<>();
            Iterator it = source.iterator();

            while (it.hasNext()) {
                Object o = it.next();
                list.add(propertiesCopy(o, clazz));
            }

            return list;
        }
    }

    /**
     * 将对象转换为map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> object2NavMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            try {
                for (Field f : fields) {
                    f.setAccessible(true);
                    map.put(f.getName(), f.get(obj));
                }
                return map;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    public static Map<String, String> object2Map(Object obj) {
        Map<String, Object> map = object2NavMap(obj);
        Map<String, String> retMap = new HashMap<>();
        map.forEach((k, v) -> retMap.put(k, v != null ? v.toString() : null));
        return retMap;
    }


    /**
     * 将map转换为对象,必须保证属性名称相同
     *
     * @return
     */
    public static Object map2Object(Map<Object, Object> map, Class<?> clazz) {
        try {
            Object target = clazz.getDeclaredConstructor().newInstance();
            if (CollectionUtils.isEmpty(map)) {
                return target;
            }
            Field[] fields = clazz.getDeclaredFields();
            if (!CollectionUtils.isEmpty(Arrays.asList(fields))) {
                Arrays.stream(fields).filter((Field field) -> map.containsKey(field.getName())).forEach(var -> {
                    //获取属性的修饰符
                    int modifiers = var.getModifiers();
                    if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                        //在lambada中结束本次循环是用return,它不支持continue和break
                        return;
                    }
                    //设置权限
                    var.setAccessible(true);
                    try {
                        var.set(target, map.get(var.getName()));
                    } catch (IllegalAccessException e) {
                        //属性类型不对,非法操作,跳过本次循环,直接进入下一次循环
                        throw new RuntimeException(e);
                    }
                });
            }
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
