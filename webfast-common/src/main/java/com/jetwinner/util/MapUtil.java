package com.jetwinner.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xulixin
 */
public final class MapUtil {

    private MapUtil() {
        // reserved.
    }

    public static boolean keyExists(Object key, Map<String, ?> map) {
        if (map != null) {
            return map.containsKey(key);
        }
        return false;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (map == null) {
            return true;
        }
        return map.size() == 0;
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> map) {
        return !isEmpty(map);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
        return new HashMap<K, V>(initialCapacity);
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<K, V>();
    }

    @SuppressWarnings("rawtypes")
    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap<K, V>(map);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(Comparator<C> comparator) {
        return new TreeMap<K, V>(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
        return new EnumMap<K, V>((type));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<K, V>();
    }

    /**
     * List<Map<String, V>?????????List<T>
     * @param clazz
     * @param list
     */
    public static <T, V> List<T> toObjectList(Class<T> clazz, List<HashMap<String, V>> list) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<T> retList = new ArrayList<T>();
        if (list != null && !list.isEmpty()) {
            for (HashMap<String, V> m : list) {
                retList.add(toObject(clazz, m));
            }
        }
        return retList;
    }

    /**
     * ???Map?????????Object
     * @param clazz ??????????????????
     * @param map ?????????Map
     */
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        T object = clazz.getDeclaredConstructor().newInstance();
        return toObject(object, map);
    }

    /**
     * ???Map?????????Object
     * @param clazz ??????????????????
     * @param map ?????????Map
     * @param toCamelCase ?????????????????????
     */
    public static <T, V> T toObject(Class<T> clazz, Map<String, V> map, boolean toCamelCase)
            throws InstantiationException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        T object = clazz.getDeclaredConstructor().newInstance();
        return toObject(object, map, toCamelCase);
    }

    /**
     * ???Map?????????Object
     * @param object ??????????????????
     * @param map ?????????Map
     */
    public static <T, V> T toObject(T object, Map<String, V> map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return toObject(object, map, false);
    }

    /**
     * ???Map?????????Object
     * @param object ??????????????????
     * @param map ?????????Map
     * @param toCamelCase ?????????????????????????????????
     */
    public static <T, V> T toObject(T object, Map<String, V> map, boolean toCamelCase) throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        if (toCamelCase) {
            map = toCamelCaseMap(map);
        }
        BeanUtils.copyProperties(map, object);
        return object;
    }

    /**
     * ?????????Map
     * @param object ????????????
     * @return ????????????????????????String
     */
    public static Map<String, String> toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return ExtraSpringBeanUtil.object2Map(object);
    }

    /**
     * ?????????Map
     * @param object ????????????
     * @return ????????????????????????????????????
     */
    public static Map<String, Object> toNavMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return ExtraSpringBeanUtil.object2NavMap(object);
    }

    /**
     * ?????????Collection<Map<K, V>>
     * @param collection ?????????????????????
     * @return ????????????Collection<Map<K, V>>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static <T> Collection<Map<String, String>> toMapList(Collection<T> collection) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                Map<String, String> map = toMap(object);
                retList.add(map);
            }
        }
        return retList;
    }

    /**
     * ?????????Collection,??????????????????????????????<Map<K, V>>
     * @param collection ?????????????????????
     * @return ????????????Collection<Map<K, V>>
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                Map<String, String> map = toMapForFlat(object);
                retList.add(map);
            }
        }
        return retList;
    }

    /**
     * ?????????Map????????????????????????????????????
     * @param object ????????????
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map<String, String> map = toMap(object);
        return toUnderlineStringMap(map);
    }

    /**
     * ???Map???Keys????????????<br>
     * (???:branch_no -> branchNo )<br>
     * @param map ?????????Map
     * @return
     */
    public static <V> Map<String, V> toCamelCaseMap(Map<String, V> map) {
        Map<String, V> newMap = new HashMap<String, V>();
        for (String key : map.keySet()) {
            newMap.put(NamingUtil.camelCase(key), map.get(key));
        }
        return newMap;
    }

    /**
     * ???Map???Keys???????????????????????????<br>
     * (???:branchNo -> branch_no)<br>
     * @param map ?????????Map
     * @return
     */
    public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
        Map<String, V> newMap = new HashMap<String, V>();
        for (String key : map.keySet()) {
            newMap.put(NamingUtil.uncamelCase(key), map.get(key));
        }
        return newMap;
    }
}
