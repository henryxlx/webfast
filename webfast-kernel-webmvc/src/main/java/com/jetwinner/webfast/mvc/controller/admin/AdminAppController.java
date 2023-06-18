package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.install.InstallControllerRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminAppController")
public class AdminAppController {

    private static Logger logger = LoggerFactory.getLogger(AdminAppController.class);

    private final DataSourceConfig dataSourceConfig;
    private final InstallControllerRegisterService installControllerRegisterService;

    public AdminAppController(DataSourceConfig dataSourceConfig,
                              InstallControllerRegisterService installControllerRegisterService) {

        this.dataSourceConfig = dataSourceConfig;
        this.installControllerRegisterService = installControllerRegisterService;
    }

    @RequestMapping({"/admin/app", "/admin/app/installed"})
    public String appInstalledPage() {
        return "/admin/app/installed";
    }

    @GetMapping("/admin/app/reinstall")
    public String appReInstallPage() {
        return "/admin/app/reinstall";
    }

    @PostMapping("/admin/app/reinstall")
    @ResponseBody
    public Boolean appReInstallAction() {
        Boolean result;
        try {
            installControllerRegisterService.addInstallControllerMapping();
            dataSourceConfig.setDataSourceDisabled(true);
            result = Boolean.TRUE;
        } catch (Exception e) {
            result = Boolean.FALSE;
        }
        return result;
    }

    @GetMapping("/admin/app/logs")
    public String changeLogPage(Model model) {
        ClassPathResource resource = new ClassPathResource("/changes.txt");
        if (!resource.exists()) {
            resource = new ClassPathResource("/webfast-changes.txt");
        }
        List<Map<String, Object>> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            String s = null;
            Map<String, Object> entry = null;
            int lines = 0;
            while ((s = br.readLine()) != null) {
                if (EasyStringUtil.isBlank(s)) {
                    continue;
                }
                if (s.startsWith("*")) {
                    if (entry != null) {
                        String html = s.replace("*", "")
                                .replaceAll("]", "</a>")
                                .replaceAll("\\[", "<a href=\"#\">");
                        Object val = null;
                        if (entry.containsKey("details")) {
                            val = entry.get("details");
                        }
                        entry.put("details", val != null ? val + html + " </br>" : html + " </br>");
                    }
                } else {
                    if (entry != null) {
                        entries.add(entry);
                    }
                    entry = new HashMap<>(3);
                    if (s.indexOf("@") != -1) {
                        String[] array = s.split("@");
                        entry.put("label", array[0]);
                        entry.put("logDate", array[1]);
                    } else {
                        entry.put("label", s);
                    }
                }
            }
            entries.add(entry);
        } catch (IOException ioe) {
            logger.error("ChangeLog Controller read file error.", ioe);
        }

        model.addAttribute("entries", entries);
        return "/admin/app/changelog";
    }
}
