package com.jetwinner.webfast.mvc.controller.install;

import com.jetwinner.platform.SystemInfoBean;
import com.jetwinner.util.MapUtil;
import com.jetwinner.util.ValueParser;
import org.apache.ibatis.jdbc.ScriptRunner;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller
public class InstallController {

    private DataSourceConfig dataSourceConfig;

    public InstallController(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    /**
     * 应用程序使用外部存储位置路径
     */
    @Value("${custom.app.storage.path}")
    private String appStoragePath;

    /**
     * 单个文件的最大上限
     */
    @Value("${spring.servlet.multipart.max-file-size}")
    private String uploadMaxFilesize;

    /**
     * 单个请求的文件总大小上限
     */
    @Value("${spring.servlet.multipart.max-request-size}")
    private String postMaxsize;

    @RequestMapping("/install")
    public String indexPage(HttpSession session) {
        session.setAttribute("step", 0);
        return "/install/index";
    }

    @GetMapping("/install/step1")
    public String step1Page(HttpSession session, Model model) {
        session.setAttribute("step", 1);
        SystemInfoBean systemInfoBean = new SystemInfoBean();
        model.addAttribute("envOs", systemInfoBean.getOsInfo());
        model.addAttribute("javaVersion", systemInfoBean.getJavaVersion());
        model.addAttribute("jvmVersion", systemInfoBean.getJvmVersion());
        model.addAttribute("mysqlOk", checkMysqlJdbcDriver());
        model.addAttribute("appStoragePath", appStoragePath);
        model.addAttribute("appStoragePathOk", checkPathExist(appStoragePath));
        model.addAttribute("uploadMaxFilesize", uploadMaxFilesize);
        model.addAttribute("postMaxsize", postMaxsize);
        return "/install/step1";
    }

    private Boolean checkMysqlJdbcDriver() {
        try {
            // This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
            Class.forName("com.mysql.jdbc.Driver");
            return Boolean.TRUE;
        } catch (ClassNotFoundException e) {
            return Boolean.FALSE;
        }
    }

    private Boolean checkPathExist(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @PostMapping("/install/step1")
    public String checkAppConfigPassAction(HttpSession session) {
        session.setAttribute("step", 2);
        return "redirect:/install/step2";
    }

    private boolean isNotCurrentStep(HttpSession session, int currentStep) {
        int step = ValueParser.parseInt(session.getAttribute("step"));
        return step != currentStep;
    }

    private String redirectSessionStepPage(HttpSession session) {
        return "redirect:/install/step" + session.getAttribute("step");
    }

    @GetMapping("/install/step2")
    public String step2Page(HttpSession session) {
        if (isNotCurrentStep(session, 2)) {
            return redirectSessionStepPage(session);
        }
        return "/install/step2";
    }

    @PostMapping("/install/step2")
    public String createDatabaseAction(DbConnectionSetting setting,
                                       HttpSession session, Model model) {

        try {
            String toTestJdbcUrl = DataSourceConfig.getMysqlJdbcUrl(setting.getHost(), setting.getPort());
            testDbConnectionOrCreateNew(toTestJdbcUrl, setting.getUser(), setting.getPassword(), setting.getDbname());

            String toNewDatabaseJdbcUrl =
                    DataSourceConfig.getMysqlJdbcUrl(setting.getHost(), setting.getPort(), setting.getDbname());
            runSqlFile("sql/mysql/webfast-kernel.sql", toNewDatabaseJdbcUrl,
                    setting.getUser(), setting.getPassword());
            buildDataSourceConfigToAppStorage("ds4install/druid/mysql/datasource.yml",
                    appStoragePath + "/datasource.yml", setting);
            session.setAttribute("step", 3);
            return "redirect:/install/step3";
        } catch (Exception e) {
            model.addAttribute("error", "数据库创建失败：" + e.getMessage());
            model.addAttribute("post", setting);
            return "/install/step2";
        }
    }

    private void testDbConnectionOrCreateNew(String jdbcUrl, String jdbcUsername, String jdbcPassword, String dbname)
            throws ActionGraspException {

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE DATABASE IF NOT EXISTS " + dbname + " DEFAULT CHARACTER SET UTF8MB4";
                stmt.executeUpdate(sql);
            } catch (SQLException se2) {
                throw new ActionGraspException("无法创建数据库！ " + se2.getMessage());
            }
        } catch (SQLException se) {
            throw new ActionGraspException("无法连接数据库！ " + se.getMessage());
        }
    }

    private void runSqlFile(String sqlFilePath, String jdbcUrl, String jdbcUsername, String jdbcPassword)
            throws SQLException, IOException {

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            ScriptRunner runner = new ScriptRunner(conn);
            // 设置不自动提交
            runner.setAutoCommit(false);
            /*
             * setStopOnError参数作用：遇见错误是否停止；
             * （1）false，遇见错误不会停止，会继续执行，会打印异常信息，并不会抛出异常，当前方法无法捕捉异常无法进行回滚操作，
             * 无法保证在一个事务内执行； （2）true，遇见错误会停止执行，打印并抛出异常，捕捉异常，并进行回滚，保证在一个事务内执行；
             */
            runner.setStopOnError(true);
            // 按照那种方式执行 方式一：true则获取整个脚本并执行； 方式二：false则按照自定义的分隔符每行执行；
            runner.setSendFullScript(false);
            runner.setDelimiter(";");
            runner.setFullLineDelimiter(false);
            // 设置是否输出日志，null不输出日志，不设置自动将日志输出到控制台
            runner.setLogWriter(null);
            runner.runScript(new BufferedReader(new InputStreamReader(
                    new ClassPathResource(sqlFilePath).getInputStream())));
        }
    }

    private void buildDataSourceConfigToAppStorage(String fromFileClasspath, String toFilePath,
                                                   DbConnectionSetting setting)
            throws IOException {

        Resource resource = new ClassPathResource(fromFileClasspath);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFilePath))) {
                String strLineData;
                while ((strLineData = reader.readLine()) != null) {
                    if (strLineData.startsWith("druid:")) {
                        writer.write(strLineData);
                        writer.newLine();
                        writer.write("  username: " + setting.getUser());
                        writer.newLine();
                        writer.write("  password: " + setting.getPassword());
                        writer.newLine();
                        writer.write("  url: " + DataSourceConfig.getMysqlJdbcUrl(setting.getHost(),
                                setting.getPort(), setting.getDbname()));
                        writer.newLine();
                    } else {
                        writer.write(strLineData);
                        writer.newLine();
                    }
                }
                writer.flush();
            }
        }
    }

    @GetMapping("/install/step3")
    public String step3Page(HttpSession session) {
        if (isNotCurrentStep(session, 3)) {
            return redirectSessionStepPage(session);
        }
        return "/install/step3";
    }

    @PostMapping("/install/step3")
    public String doSiteConfigAction(HttpSession session) {
        session.setAttribute("step", 4);
        return "redirect:/install/step4";
    }

    @GetMapping("/install/step4")
    public String step4Page(HttpSession session) {
        if (isNotCurrentStep(session, 4)) {
            return redirectSessionStepPage(session);
        }
        dataSourceConfig.setDataSourceDisabled(false);
        return "/install/step4";
    }

    @PostMapping("/install/finish")
    @ResponseBody
    public Map<String, Object> finishAction() {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("error", "can not connect cloud server.");
        return map;
    }
}
