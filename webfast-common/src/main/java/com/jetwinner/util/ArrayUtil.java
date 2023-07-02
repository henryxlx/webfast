package com.jetwinner.util;

import java.lang.reflect.Array;

/**
 * @author xulixin
 */
public final class ArrayUtil {

    public static final int INDEX_NOT_FOUND = -1;

    private ArrayUtil() {
        // reserved.
    }

    public static boolean isEmpty(final boolean[] array) {
        return getLength(array) == 0;
    }

    public static boolean isEmpty(final Object[] array) {
        return getLength(array) == 0;
    }

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean inArray(Object objectToFind, String... array) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    public static boolean isArray(Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    public static boolean isNotArray(Object obj) {
        return !isArray(obj);
    }

    public static String[] toStringArray(Object target) {
        String[] arr = new String[0];
        if (target != null) {
            if (target.getClass().isArray()) {
                if (target.getClass().equals(String[].class)) {
                    arr = (String[]) target;
                } else {
                    Object[] objArray = (Object[]) target;
                    int len = objArray.length;
                    if (len > 0) {
                        arr = new String[len];
                        for (int i = 0; i < len; i++) {
                            arr[i] = String.valueOf(objArray[i]);
                        }
                    }
                }
            }
        }
        return arr;
    }

    public static int count(Object objMass) {
        if (objMass.getClass().isArray()) {
            Object[] objArray = (Object[]) objMass;
            return objArray.length;
        }
        return objMass == null ? 0 : 1;
    }

    public static Object[] toArray(Object objMass) {
        if (objMass != null) {
            if (objMass.getClass().isArray()) {
                return (Object[]) objMass;
            }
        }
        return objMass == null ? new Object[0] : new Object[]{objMass};
    }
}
