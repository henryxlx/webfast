package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.platform.SystemInfoBean;
import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.mvc.block.BlockRenderController;
import com.jetwinner.webfast.mvc.block.BlockRenderMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminDefaultController")
public class DefaultController implements BlockRenderController {

    private final UserAccessControlService userAccessControlService;

    private final ServletContext servletContext;

    private DataSource dataSource;

    public DefaultController(UserAccessControlService userAccessControlService,
                             ServletContext servletContext, DataSource dataSource) {

        this.userAccessControlService = userAccessControlService;
        this.servletContext = servletContext;
        this.dataSource = dataSource;
    }

    @RequestMapping({"/admin", "/admin/", "/admin/index"})
    public String indexPage() {
        return "/admin/default/index";
    }

    @RequestMapping("/admin/default/getCloudNotice")
    @BlockRenderMethod
    public String getCloudNoticePage() {
        return "/admin/default/cloud-notice";
    }

    @RequestMapping("/admin/system/status")
    public String systemStatusPage(Model model) {
        SystemInfoBean systemInfoBean = new SystemInfoBean();
        model.addAttribute("systemMap", systemInfoBean.getSystemMap());
        model.addAttribute("javaMap", systemInfoBean.getJavaMap());
        model.addAttribute("jvmMap", systemInfoBean.getJavaVirtualMachineMap());
        model.addAttribute("percentageUsed", "" + systemInfoBean.getPercentageUsed());
        model.addAttribute("percentageFree", "" + systemInfoBean.getPercentageFree());

        if (dataSource != null) {
            try (Connection conn = dataSource.getConnection()) {
                DatabaseMetaData md = conn.getMetaData();
                model.addAttribute("dbinfo", md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
                model.addAttribute("driverinfo", md.getDriverName() + " " + md.getDriverVersion());
            } catch (Exception e) {
                throw new RuntimeGoingException("Fetch database and driver information error: " + e.getMessage());
            }
        }

        if (servletContext != null) {
            model.addAttribute("serverInfo", servletContext.getServerInfo());
            model.addAttribute("servletVersion", "" + servletContext.getMajorVersion()
                    + "." + servletContext.getMinorVersion());
        }
        return "/admin/default/system-status";
    }

    @RequestMapping("/admin/popular/courses")
    public String popularCoursesPage() {
        return "/admin/default/popular-courses";
    }

    @RequestMapping("/admin/operation/analysis")
    public String operationAnalysisDashboardPage() {
        return "/admin/default/operation-analysis-dashboard";
    }

    @RequestMapping("/admin/online/count")
    @ResponseBody
    public Map<String, Object> onlineCountAction() {
        Map<String, Object> map = new HashMap<>(2);
        // statisticsService.getOnlineCount(15 * 60);
        map.put("onlineCount", userAccessControlService.getOnlineCount());
        map.put("message", "ok");
        return map;
    }

    @RequestMapping("/admin/login/count")
    @ResponseBody
    public Map<String, Object> loginCountAction() {
        Map<String, Object> map = new HashMap<>(2);
        // statisticsService.getloginCount(15*60));
        map.put("loginCount", 0);
        map.put("message", "ok");
        return map;
    }
}
