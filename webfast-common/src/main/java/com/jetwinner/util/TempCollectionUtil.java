package com.jetwinner.util;

import java.util.*;

public class TempCollectionUtil {

    public static List<Map<String, Object>> arraySlice(Collection<Map<String, Object>> collection,
                                                       int start, int count) {

        if (collection != null) {
            List<Map<String, Object>> list = new ArrayList<>();
            if (collection.size() <= count) {
                list.addAll(collection);
            } else {
                Iterator<Map<String, Object>> it = collection.iterator();
                int i = 0;
                int counter = count;
                while (it.hasNext() || counter > 0) {
                    Map<String, Object> entry = it.next();
                    if (i >= start) {
                        list.add(entry);
                        counter--;
                    }
                }
            }
            return list;
        }
        return new ArrayList<>(0);
    }
}
