package com.jetwinner.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilTest {

    @Test
    public void inArray() {
        String type = "default";
        assertTrue(ArrayUtil.inArray(type, "default", "phpwind", "discuz"));
        type = "wind";
        assertFalse(ArrayUtil.inArray(type, "default", "phpwind", "discuz"));
    }
}