package com.jetwinner.webfast.database;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.kernel.typedef.ParamMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xulixin
 */
public final class FastDatabaseUtil {

    private static Map<String, DatabaseDumper> cachedMap = new HashMap<>(3);

    public static final String MYSQL_DUMPER_KEY = "MYSQL_DUMPER";

    private FastDatabaseUtil() {
        // reserved.
    }

    public static void addDumper(String key, DatabaseDumper dumper) {
        cachedMap.put(key, dumper);
    }

    public static String backupdb(String uploadTmpPath) {
        String backupDir = uploadTmpPath + File.separator + UUID.randomUUID() + ".txt";
        Map<String, Object> dbSetting = new ParamMap().add("exclude", ListUtil.newArrayList("session", "cache")).toMap();
        DatabaseDumper dump = cachedMap.get(MYSQL_DUMPER_KEY);
        if (dump != null) {
            dump.setDbSetting(dbSetting);
            return dump.export(backupDir);
        }
        return "Database backup failed! Not found database dumper.";
    }
}
