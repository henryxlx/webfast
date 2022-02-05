package com.jetwinner.toolbag;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author xulixin
 */
public class MapKitOnJava8 {

    private MapKitOnJava8() {
        // reserved.
    }

    public static <T> void filter(Map<String, T> map) {
        filter(map, Objects::nonNull);
    }

    public static <T> void filter(Map<String, T> map, Predicate<T> judgeForKeep) {
        Assert.notNull(map, "MapToolkitOnJava8::filter parameter map must not be null.");
        Iterator<Map.Entry<String, T>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, T> entry = it.next();
            T val = entry.getValue();
            if (!judgeForKeep.test(val)) {
                it.remove();
            }
        }
    }

    public static <T> Map<String, T> filterToNewMap(Map<String, T> map) {
        return filterToNewMap(map, Objects::nonNull);
    }

    public static <T> Map<String, T> filterToNewMap(Map<String, T> map, Predicate<T> judgeForKeep) {
        Assert.notNull(map, "MapToolkitOnJava8::filterToNewMap parameter map must not be null.");
        Map<String, T> newMap = new HashMap<>(map.size());
        map.forEach((k, v) -> {
            if (judgeForKeep.test(v)) {
                newMap.put(k, v);
            }
        });
        return newMap;
    }
}
