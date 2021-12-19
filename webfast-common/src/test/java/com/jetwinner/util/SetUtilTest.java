package com.jetwinner.util;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class SetUtilTest {

    @Test
    public void testNewHashSetWithArray() {
        String[] strArray = {"ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER"};
        strArray = "ROLE_USER|ROLE_ADMIN|ROLE_SUPER|".split("\\|");
        Set<String> roles = SetUtil.newHashSet(strArray);
        assertEquals(3, roles.size());
        for (String s : strArray) {
            assertTrue("Set contains " + s, roles.contains(s));
        }
        strArray = "|ROLE_USER|ROLE_ADMIN|ROLE_SUPER|".split("\\|");
        roles = SetUtil.newHashSet(strArray);
        assertEquals(4, roles.size());
        assertTrue("Set contains blank string", roles.contains(""));
    }
}