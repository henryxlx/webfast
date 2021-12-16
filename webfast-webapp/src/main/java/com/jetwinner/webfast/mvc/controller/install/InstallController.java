package com.jetwinner.webfast.mvc.controller.install;

import com.jetwinner.platform.SystemInfoBean;
import com.jetwinner.util.MapUtil;
import com.jetwinner.util.StringEncoderUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.AppWorkingConstant;
import com.jetwinner.webfast.kernel.dao.DataSourceConfig;
import com.jetwinner.webfast.kernel.exception.ActionGraspException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    private static final String STEP_KEY = "step";
    private static final int STEP_0 = 0;
    private static final int STEP_1 = 1;
    private static final int STEP_2 = 2;
    private static final int STEP_3 = 3;
    private static final int STEP_4 = 4;

    private final DataSourceConfig dataSourceConfig;
    private final AppWorkingConstant appWorkingConstant;

    public InstallController(DataSourceConfig dataSourceConfig,
                             AppWorkingConstant appWorkingConstant) {

        this.dataSourceConfig = dataSourceConfig;
        this.appStoragePath = appWorkingConstant.getStoragePath();
        this.appWorkingConstant = appWorkingConstant;
    }

    /**
     * 应用程序使用外部存储位置路径
     */
    private final String appStoragePath;

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

    private ModelAndView toModelAndView() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("appConst", appWorkingConstant);
        return mav;
    }

    @RequestMapping("/install")
    public ModelAndView indexPage(HttpSession session) {
        ModelAndView mav = toModelAndView();
        session.setAttribute(STEP_KEY, STEP_0);
        mav.setViewName("/install/index");
        return mav;
    }

    @GetMapping("/install/step1")
    public ModelAndView step1Page(HttpSession session) {
        ModelAndView mav = toModelAndView();
        session.setAttribute(STEP_KEY, STEP_1);
        SystemInfoBean systemInfoBean = new SystemInfoBean();
        mav.addObject("envOs", systemInfoBean.getOsInfo());
        mav.addObject("javaVersion", systemInfoBean.getJavaVersion());
        mav.addObject("jvmVersion", systemInfoBean.getJvmVersion());
        mav.addObject("mysqlOk", checkMysqlJdbcDriver());
        mav.addObject("appStoragePath", appStoragePath);
        mav.addObject("appStoragePathOk", checkPathExist(appStoragePath));
        mav.addObject("uploadMaxFilesize", uploadMaxFilesize);
        mav.addObject("postMaxsize", postMaxsize);
        mav.setViewName("/install/step1");
        return mav;
    }

    private Boolean checkMysqlJdbcDriver() {
        try {
            // Driver class `com.mysql.jdbc.Driver` is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver`.
            Class.forName(DataSourceConfig.MYSQL_NEW_DRIVER_CLASS);
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
        session.setAttribute(STEP_KEY, STEP_2);
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
    public ModelAndView step2Page(HttpSession session) {
        ModelAndView mav = toModelAndView();
        if (isNotCurrentStep(session, STEP_2)) {
            mav.setViewName(redirectSessionStepPage(session));
        } else {
            mav.setViewName("/install/step2");
        }
        return mav;
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
            session.setAttribute(STEP_KEY, STEP_3);
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
                new InputStreamReader(resource.getInputStream(), AppWorkingConstant.CHARSET_UTF8))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFilePath))) {
                String strLineData;
                while ((strLineData = reader.readLine()) != null) {
                    if (strLineData.startsWith("druid:")) {
                        writer.write(strLineData);
                        writer.newLine();
                        writer.write("  username: " + StringEncoderUtil.encode(setting.getUser()));
                        writer.newLine();
                        writer.write("  password: " + StringEncoderUtil.encode(setting.getPassword()));
                        writer.newLine();
                        String url = DataSourceConfig.getMysqlJdbcUrl(setting.getHost(),
                                setting.getPort(), setting.getDbname());
                        writer.write("  url: " + StringEncoderUtil.encode(url));
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
    public ModelAndView step3Page(HttpSession session) {
        ModelAndView mav = toModelAndView();
        if (isNotCurrentStep(session, STEP_3)) {
            mav.setViewName(redirectSessionStepPage(session));
        } else {
            mav.setViewName("/install/step3");
        }
        return mav;
    }

    @PostMapping("/install/step3")
    public String doSiteConfigAction(HttpSession session) {
        session.setAttribute(STEP_KEY, STEP_4);
        return "redirect:/install/step4";
    }

    @GetMapping("/install/step4")
    public ModelAndView step4Page(HttpSession session) {
        ModelAndView mav = toModelAndView();
        if (isNotCurrentStep(session, STEP_4)) {
            mav.setViewName(redirectSessionStepPage(session));
        } else {
            dataSourceConfig.setDataSourceDisabled(false);
            mav.setViewName("/install/step4");
        }
        return mav;
    }

    @PostMapping("/install/finish")
    @ResponseBody
    public Map<String, Object> finishAction() {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("error", "can not connect cloud server.");
        return map;
    }
}
