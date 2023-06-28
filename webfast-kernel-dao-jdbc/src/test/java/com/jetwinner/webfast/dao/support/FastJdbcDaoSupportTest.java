package com.jetwinner.webfast.dao.support;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FastJdbcDaoSupportTest {

    private FastJdbcDaoSupport daoSupport;

    @Before
    public void setup() {
        daoSupport = new FastJdbcDaoSupport();
    }

    @Test
    public void repeatQuestionMarker() {
        String marks = daoSupport.repeatQuestionMark(3);
        assertEquals("?, ?, ?", marks);
    }

    @Test
    public void repeatMark() {
        String marks = daoSupport.repeatMark('?', 5);
        assertEquals("?, ?, ?, ?, ?", marks);
    }

    @Test
    public void testRepeatMark() {
        String marks = daoSupport.repeatMark('@', 5, ':');
        assertEquals("@: @: @: @: @", marks);
        marks = daoSupport.repeatMark('|', 5, ' ');
        assertEquals("|  |  |  |  |", marks);
    }

    @Test
    public void joinInStringValues() {
        String[] arr = {"name", "title", "age", "gender"};
        String str = daoSupport.joinInStringValues(arr);
        String expectedStr = "'name', 'title', 'age', 'gender'";
        assertEquals(expectedStr, str);
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("title");
        list.add("age");
        list.add("gender");
        assertEquals(expectedStr, str);
    }
}