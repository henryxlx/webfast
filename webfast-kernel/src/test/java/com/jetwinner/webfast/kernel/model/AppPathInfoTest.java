package com.jetwinner.webfast.kernel.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppPathInfoTest {

    @Test
    public void testPathInfo() {
        String path = "d:/webfast/appdata/web/files/tmp/user_3_ecf8427e!png";
        AppPathInfo pathInfo = new AppPathInfo(path, "!");
        assertEquals("d:/webfast/appdata/web/files/tmp", pathInfo.getDirname());
        assertEquals("user_3_ecf8427e", pathInfo.getFilename());
        assertEquals("png", pathInfo.getExtension());
        pathInfo = new AppPathInfo(path);
        assertEquals("d:/webfast/appdata/web/files/tmp", pathInfo.getDirname());
        assertEquals("user_3_ecf8427e!png", pathInfo.getFilename());
        assertEquals("", pathInfo.getExtension());
        pathInfo = new AppPathInfo("d:/webfast/appdata/web/files/tmp/user_3_ecf8427e.png");
        assertEquals("d:/webfast/appdata/web/files/tmp", pathInfo.getDirname());
        assertEquals("user_3_ecf8427e", pathInfo.getFilename());
        assertEquals("png", pathInfo.getExtension());
    }

}