package com.jetwinner.toolbag;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileToolkitTest {

    @Test
    public void hashFilename() {
        String hashStr = FileToolkit.hashFilename("user_3_");
        System.out.println("Hash String with MD5 =" + hashStr);
        System.out.println("Hash String length =" + hashStr.length());
        assertEquals(8, hashStr.length());
    }
}