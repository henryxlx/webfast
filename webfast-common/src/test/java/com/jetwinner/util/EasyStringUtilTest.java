package com.jetwinner.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EasyStringUtilTest {

    @Test
    public void isBlank() {
        assertTrue(EasyStringUtil.isBlank(null));
        assertTrue(EasyStringUtil.isBlank(""));
        assertTrue(EasyStringUtil.isBlank(" "));
        assertFalse(EasyStringUtil.isBlank("bob"));
        assertFalse(EasyStringUtil.isBlank("  bob  "));
    }

    @Test
    public void isNotBlank() {
        assertFalse(EasyStringUtil.isNotBlank(null));
        assertFalse(EasyStringUtil.isNotBlank(""));
        assertFalse(EasyStringUtil.isNotBlank(" "));
        assertTrue(EasyStringUtil.isNotBlank("bob"));
        assertTrue(EasyStringUtil.isNotBlank("  bob  "));
    }

    @Test
    public void testObjectIsBlank() {
        Object obj = null;
        assertTrue(EasyStringUtil.isBlank(obj));
        obj = Integer.valueOf(0);
        assertFalse(EasyStringUtil.isBlank(obj));
        obj = new Object[0];
        assertFalse(EasyStringUtil.isBlank(obj));
        obj = new ArrayList();
        assertFalse(EasyStringUtil.isBlank(obj));
    }

    @Test
    public void testObjectIsNotBlank() {
        Object obj = null;
        assertFalse(EasyStringUtil.isNotBlank(obj));
        obj = Integer.valueOf(0);
        assertTrue(EasyStringUtil.isNotBlank(obj));
    }

    @Test
    public void isEmpty() {
        assertTrue(EasyStringUtil.isEmpty(null));
        assertTrue(EasyStringUtil.isEmpty(""));
        assertFalse(EasyStringUtil.isEmpty(" "));
        assertFalse(EasyStringUtil.isEmpty("bob"));
        assertFalse(EasyStringUtil.isEmpty("  bob  "));
    }

    @Test
    public void isNotEmpty() {
        assertFalse(EasyStringUtil.isNotEmpty(null));
        assertFalse(EasyStringUtil.isNotEmpty(""));
        assertTrue(EasyStringUtil.isNotEmpty(" "));
        assertTrue(EasyStringUtil.isNotEmpty("bob"));
        assertTrue(EasyStringUtil.isNotEmpty("  bob  "));
    }

    @Test
    public void isNumeric() {
        assertFalse(EasyStringUtil.isNumeric(null));
        assertFalse(EasyStringUtil.isNumeric(""));
        assertFalse(EasyStringUtil.isNumeric("  "));
        assertTrue(EasyStringUtil.isNumeric("123"));
        assertTrue(EasyStringUtil.isNumeric("\u0967\u0968\u0969"));
        assertFalse(EasyStringUtil.isNumeric("12 3"));
        assertFalse(EasyStringUtil.isNumeric("ab2c"));
        assertFalse(EasyStringUtil.isNumeric("12-3"));
        assertFalse(EasyStringUtil.isNumeric("12.3"));
        assertFalse(EasyStringUtil.isNumeric("-123"));
        assertFalse(EasyStringUtil.isNumeric("+123"));
    }

    @Test
    public void testEquals() {
        String s1 = "abc", s2 = "abc";
        assertTrue(EasyStringUtil.equals(s1, s2));
        s1 = null;
        assertFalse(EasyStringUtil.equals(s1, s2));
        s1 = "abc"; s2 = null;
        assertFalse(EasyStringUtil.equals(s1, s2));
        s2 = "123";
        assertFalse(EasyStringUtil.equals(s1, s2));
        s1 = null; s2 = null;
        assertTrue(EasyStringUtil.equals(s1, s2));
    }

    @Test
    public void testNotEquals() {
        String s1 = "abc", s2 = "abc";
        assertFalse(EasyStringUtil.notEquals(s1, s2));
        s1 = null;
        assertTrue(EasyStringUtil.notEquals(s1, s2));
        s1 = "abc";
        s2 = null;
        assertTrue(EasyStringUtil.notEquals(s1, s2));
        s2 = "123";
        assertTrue(EasyStringUtil.notEquals(s1, s2));
        s1 = null; s2 = null;
        assertFalse(EasyStringUtil.notEquals(s1, s2));
    }
}