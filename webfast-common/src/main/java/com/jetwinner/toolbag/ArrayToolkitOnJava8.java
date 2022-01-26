package com.jetwinner.toolbag;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
public final class ArrayToolkitOnJava8 {

    private ArrayToolkitOnJava8() {
        // reserved.
    }

    public static <T> Map<String, T> index(List<T> list, Function<T, Object> keyMapper) {
        Map<Object, T> map = list.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
        Map<String, T> retMap = new HashMap<>(map.size());
        map.forEach((k, v) -> retMap.put(String.valueOf(k), v));
        return retMap;
    }

    public static <T> Set<Object> column(List<T> list, Function<T, Object> mapper) {
        Assert.notNull(list, "ArrayToolkitOnJava8::column parameter list must not be null.");
        return list.stream().map(mapper).collect(Collectors.toSet());
    }
}
