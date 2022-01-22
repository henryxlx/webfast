package com.jetwinner.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhpStringUtilTest {

    @Test
    public void ltrim() {
        assertEquals("myuri/test/", PhpStringUtil.ltrim("////// /   /myuri/test/", " /"));
    }

    @Test
    public void rtrim() {
        assertEquals("http://mytest.com", PhpStringUtil.rtrim("http://mytest.com/   ////// /   /", " /"));
    }

    @Test
    public void dirname() {
        assertEquals("\\12-14", PhpStringUtil.dirname("D:\\webfast\\files\\default\\2021\\12-14\\user_3_ecf8427e.png"));
        assertEquals("/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png"));
        assertEquals("/default/2021/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", 3));
        assertEquals("/webfast/files/default/2021/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", 5));
        assertEquals("D:/webfast/files/default/2021/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", 6));
        assertEquals("D:/webfast/files/default/2021/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", 7));
        assertEquals("/12-14", PhpStringUtil.dirname("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", 0));
    }

    @Test
    public void basename() {
        assertEquals("user_3_ecf8427e.png", PhpStringUtil.basename("D:\\webfast\\files\\default\\2021\\12-14\\user_3_ecf8427e.png"));
        assertEquals("user_3_ecf8427e.png", PhpStringUtil.basename("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png"));
        assertEquals("user_3_ecf8427e", PhpStringUtil.basename("D:/webfast/files/default/2021/12-14/user_3_ecf8427e.png", ".png"));
        assertEquals("D:webfastfilesdefault202112-14user_3_ecf8427e.png", PhpStringUtil.basename("D:webfastfilesdefault202112-14user_3_ecf8427e.png"));
        assertEquals("D:webfastfilesdefault202112-14user_3_ecf8427e", PhpStringUtil.basename("D:webfastfilesdefault202112-14user_3_ecf8427e.png", ".png"));
    }

    @Test
    public void ucfirst() {
        assertEquals("Default", PhpStringUtil.ucfirst("default"));
        assertEquals("BIG", PhpStringUtil.ucfirst("BIG"));
        assertEquals("123default", PhpStringUtil.ucfirst("123default"));
    }
}