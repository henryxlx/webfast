package com.jetwinner.toolbag;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MapKitOnJava8Test {

    @Test
    public void filter() {
        Map<String, Object> map = new HashMap<>();
        map.put("keyForNull", null);
        map.put("keyForName", "testValue");
        MapKitOnJava8.filter(map);
        assertFalse(map.containsKey("keyForNull"));
        assertTrue(map.containsKey("keyForName"));
    }

    @Test
    public void testFilter() {
        Map<String, Object> map = new HashMap<>();
        map.put("keyForNull", null);
        map.put("keyForName", "testValue");
        MapKitOnJava8.filter(map, Objects::isNull);
        assertTrue(map.containsKey("keyForNull"));
        assertFalse(map.containsKey("keyForName"));
    }

    @Test
    public void filterToNewMap() {
        Map<String, Object> fromMap = new HashMap<>();
        fromMap.put("keyForNull", null);
        fromMap.put("keyForName", "testValue");
        Map<String, Object> map = MapKitOnJava8.filterToNewMap(fromMap);
        assertTrue(fromMap.containsKey("keyForNull"));
        assertTrue(fromMap.containsKey("keyForName"));
        assertFalse(map.containsKey("keyForNull"));
        assertTrue(map.containsKey("keyForName"));
    }

    @Test
    public void testFilterToNewMap() {
        Map<String, Object> fromMap = new HashMap<>();
        fromMap.put("keyForNull", null);
        fromMap.put("keyForName", "testValue");
        Map<String, Object> map = MapKitOnJava8.filterToNewMap(fromMap, Objects::isNull);
        assertTrue(fromMap.containsKey("keyForNull"));
        assertTrue(fromMap.containsKey("keyForName"));
        assertTrue(map.containsKey("keyForNull"));
        assertFalse(map.containsKey("keyForName"));
    }
}