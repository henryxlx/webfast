package com.jetwinner.webfast.dao.support;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FastJdbcDaoSupportTest {

    @Test
    public void repeatQuestionMarker() {
        FastJdbcDaoSupport daoSupport = new FastJdbcDaoSupport();
        String marks = daoSupport.repeatQuestionMark(3);
        assertEquals("?, ?, ?", marks);
    }

    @Test
    public void repeatMark() {
        FastJdbcDaoSupport daoSupport = new FastJdbcDaoSupport();
        String marks = daoSupport.repeatMark('?', 5);
        assertEquals("?, ?, ?, ?, ?", marks);
    }

    @Test
    public void testRepeatMark() {
        FastJdbcDaoSupport daoSupport = new FastJdbcDaoSupport();
        String marks = daoSupport.repeatMark('@', 5, ':');
        assertEquals("@: @: @: @: @", marks);
        marks = daoSupport.repeatMark('|', 5, ' ');
        assertEquals("|  |  |  |  |", marks);
    }
}