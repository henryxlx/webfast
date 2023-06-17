package com.jetwinner.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public final class FastHashMap {

    public static FastHashMap build() {
        return new FastHashMap();
    }

    public static FastHashMap build(int size) {
        return new FastHashMap(size);
    }

    public static FastHashMap build(Map<String, Object> map) {
        return new FastHashMap(map);
    }

    private final Map<String, Object> mapForData;

    private FastHashMap() {
        this.mapForData = new HashMap<>();
    }

    private FastHashMap(int size) {
        this.mapForData = new HashMap<>(size);
    }

    private FastHashMap(Map<String, Object> mapForData) {
        this.mapForData = mapForData;
    }

    public FastHashMap add(String key, Object value) {
        this.mapForData.put(key, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return this.mapForData;
    }
}