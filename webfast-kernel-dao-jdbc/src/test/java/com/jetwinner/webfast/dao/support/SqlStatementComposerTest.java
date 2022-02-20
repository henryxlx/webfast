package com.jetwinner.webfast.dao.support;

import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqlStatementComposerTest {

    @Test
    public void getUpdateSql() {
        String sql = SqlStatementComposer.getUpdateSql("app_content",
                new ParamMap().add("status", "trash").add("id", 3).toMap(), "id");
        assertEquals("UPDATE app_content SET status = :status WHERE id = :id", sql);
    }
}