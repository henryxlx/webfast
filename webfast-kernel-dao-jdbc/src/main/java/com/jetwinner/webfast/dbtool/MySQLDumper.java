package com.jetwinner.webfast.dbtool;

import com.jetwinner.util.*;
import com.jetwinner.webfast.database.DatabaseDumper;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public class MySQLDumper implements DatabaseDumper {

    private boolean insertExclude = false;
    private Map<String, Object> dbSettings = MapUtil.newHashMap(0);
    private final Map<String, Object> internelSetting = new ParamMap()
            .add("exclude", ListUtil.newArrayList())
            .add("isDropTable", Boolean.TRUE)
            .add("hasTransaction", Boolean.FALSE)  // 默认是Boolean.True启用事务，在备份中存在问题jdbcTemplate的机制要深入研究
            .add("lockread", Boolean.FALSE)  // 默认是Boolean.True读锁定，在备份中存在问题jdbcTemplate的机制要深入研究
            .add("lockwrite", Boolean.FALSE)  // 默认是Boolean.True写锁定，在备份中存在问题jdbcTemplate的机制要深入研究
            .add("isextend", Boolean.TRUE).toMap();

    private final QuickJdbcOperationsTemplate jdbcTemplate;
    private MySQLMetaData metaData = new MySQLMetaData();

    public MySQLDumper(DataSource dataSource) {
        this.metaData.parse(dataSource);
        this.jdbcTemplate = new QuickJdbcOperationsTemplate(dataSource);
        this.jdbcTemplate.execute("SET NAMES utf8");
    }

    private Object getSet(String key) {
        return getSet(key, Object.class);
    }

    @SuppressWarnings("unchecked")
    private <T> T getSet(String key, Class<T> targetClass) {
        Object result;
        if (this.dbSettings.containsKey(key)) {
            result = this.dbSettings.get(key);
        } else {
            result = this.internelSetting.get(key);
        }
        if (Boolean.class == targetClass) {
            return result instanceof Boolean ? (T) result : (T) Boolean.valueOf(String.valueOf(result));
        } else if (List.class == targetClass && result instanceof List) {
            return (T) result;
        } else {
            throw new IllegalArgumentException(
                    "Cannot convert value of  [" + key + "] to target class [" + targetClass.getName() + "]");
        }
    }

    @Override
    public void setDbSetting(Map<String, Object> dbSetting) {
        if (MapUtil.isEmpty(dbSetting)) {
            this.dbSettings = dbSetting;
        }
    }

    @Override
    public String export(String target) {
        try {
            return exportNative(target);
        } catch (IOException e) {
            throw new RuntimeGoingException(e.getMessage());
        }
    }

    private String exportNative(String target) throws IOException {
        target = target + ".gz";

        if (FastDirectoryUtil.dirNotExists(FastDirectoryUtil.getDirPath(target))) {
            throw new RuntimeGoingException("无导出目录写权限，无法导出数据库");
        }

        List<String> tables = jdbcTemplate.queryForList("SHOW TABLES", String.class);

        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), StandardCharsets.UTF_8));
        this.lineWrite(out, "-- --------------------------------------------------\n");
        this.lineWrite(out, "-- ---------------- 导出数据库\n");
        this.lineWrite(out, "-- ---------------- 时间:" + EasyDateUtil.today("yyyy-MM-dd HH:mm:ss") + " \n\n");
        this.lineWrite(out, "-- ---------------- 数据库:" + this.metaData.database + " \n\n");
        this.lineWrite(out, "-- ---------------- 主机:" + this.metaData.host + " \n\n");
        this.lineWrite(out, "-- --------------------------------------------------\n\n\n");

        List excludeList = this.getSet("exclude", List.class);
        String[] excludeTableNames = new String[excludeList.size()];
        for (int i = 0, len = excludeTableNames.length; i < len; i++) {
            excludeTableNames[i] = String.valueOf(excludeList.get(i));
        }
        for (String table : tables) {
            if (this.exportTableCreateSql(out, table)) {
                if (ArrayUtil.inArray(table, excludeTableNames)) {
                    continue;
                }
                try {
                    this.exportValues(out, table);
                } catch (Exception e) {
                    this.jdbcTemplate.rollBack();
                    if (this.getSet("lockread", Boolean.class)) {
                        jdbcTemplate.execute("UNLOCK TABLES");
                    }
                    throw new RuntimeGoingException(e.getMessage());
                }
            }
        }
        out.flush();
        out.close();
        return target;
    }

    private void lineWrite(Writer out, String line) {
        try {
            out.write(line);
        } catch (IOException e) {
            throw new RuntimeGoingException(String.format("Error: %s, When write line %s", e.getMessage(), line));
        }
    }

    private boolean exportTableCreateSql(Writer out, String table) {
        String sql = "SHOW CREATE TABLE " + table;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        StringBuilder line = new StringBuilder();
        for (Map<String, Object> row : rows) {
            if (EasyStringUtil.isNotBlank(row.get("Create Table"))) {
                line.append("-- --------------------------------------------------\n\n");
                line.append(String.format("-- -------------- 表:%s 的表结构  -------------- \n\n", table));
                line.append("-- --------------------------------------------------\n\n");

                if (this.getSet("isDropTable", Boolean.class)) {
                    line.append(" DROP TABLE IF EXISTS ").append(table).append(" ; \n");
                }
                line.append(row.get("Create Table"));
                line.append(";\n\n");
                this.lineWrite(out, line.toString());
                return true;
            }

            if (EasyStringUtil.isNotBlank(row.get("Create View"))) {
                line.append("-- --------------------------------------------------\n\n");
                line.append(String.format("-- -------------- 视图:%s 的表结构  -------------- \n\n", table));
                line.append("-- --------------------------------------------------\n\n");
                line.append(row.get("Create View"));
                line.append(";\n\n");

                this.lineWrite(out, line.toString());
                return false;
            }
        }
        return false;
    }


    private void exportValues(Writer out, String table) {

        this.lineWrite(out, String.format("-- ----------- %s 的数据 ---------\n\n", table));

        if (this.getSet("hasTransaction", Boolean.class)) {
//            this.jdbcTemplate.setTransactionIsolation(3);
            this.jdbcTemplate.beginTransaction();
        }
        if (this.getSet("lockread", Boolean.class)) {
            jdbcTemplate.execute(String.format("LOCK TABLES `%s` READ LOCAL", table));
        }

        if (this.getSet("lockwrite", Boolean.class)) {
            this.lineWrite(out, String.format("LOCK TABLES `%s` WRITE;\n", table));
        }

        this.insertExclude = true;
        String sql = String.format("SELECT * FROM `%s`", table);
        jdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    if (columnCount < 1) {
                        continue;
                    }
                    String[] vals = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        Object val = rs.getObject(i + 1);
                        vals[i] = val == null ? "NULL" : quote(String.valueOf(val));
                    }
                    if (vals == null || vals.length < 1) {
                        continue;
                    }
                    if (insertExclude || !getSet("isextend", Boolean.class)) {
                        lineWrite(out,
                                "INSERT INTO " + table + " VALUES (" + EasyStringUtil.implode(",", vals) + ")");
                        insertExclude = false;
                    } else {
                        lineWrite(out, ",(" + EasyStringUtil.implode(",", vals) + ")");
                    }
                }
            }
        });
        if (!insertExclude) {
            this.lineWrite(out, ";\n\n");
        }
        if (this.getSet("lockwrite", Boolean.class)) {
            this.lineWrite(out, "UNLOCK TABLES;\n");
        }
        if (this.getSet("hasTransaction", Boolean.class)) {
            this.jdbcTemplate.commit();
        }
        if (this.getSet("lockread", Boolean.class)) {
            jdbcTemplate.execute("UNLOCK TABLES");
        }
        this.lineWrite(out, "\n\n");
        this.lineWrite(out, String.format("-- ----------- %s 的数据结束 ---------\n\n", table));
    }

    private String quote(String value) {
        return this.metaData.quoteString + value + this.metaData.quoteString;
    }
}

class MySQLMetaData {

    String quoteString = "'";
    String host = "unknown";
    String database = "unknown";

    public void parse(DataSource dataSource) {
        try {
            this.database = dataSource.getConnection().getSchema();
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            this.quoteString = metaData.getIdentifierQuoteString();
            this.host = metaData.getURL();
        } catch (SQLException e) {
            throw new RuntimeGoingException("MySQLDumper fetch database meta data failed. " + e.getMessage());
        }
    }
}
