package com.jetwinner.webfast.database;

import java.util.Map;

public interface DatabaseDumper {

    String export(String backupDir);

    void setDbSetting(Map<String, Object> dbSetting);
}
