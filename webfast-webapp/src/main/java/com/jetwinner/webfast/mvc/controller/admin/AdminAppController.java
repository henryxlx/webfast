package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.datasource.DataSourceConfig;
import com.jetwinner.webfast.kernel.service.InstallControllerRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xulixin
 */
@Controller("webfastAdminAppController")
public class AdminAppController {

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
}
