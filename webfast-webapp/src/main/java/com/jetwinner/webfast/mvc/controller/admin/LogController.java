package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminLogController")
public class LogController {

    private final AppLogService logService;
    private final AppUserService userService;

    public LogController(AppLogService logService, AppUserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @RequestMapping("/admin/logs")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ParamMap.toConditionMap(request);
        Map<String, Object> conditions = ParamMap.filterConditionMap(fields,
                "startDateTime", "endDateTime", "nickname", "level");

        Paginator paginator = new Paginator(request,
                logService.searchLogCount(conditions), 30);
        List<Map<String, Object>> logs = logService.searchLogs(
            conditions,
            "created",
            paginator.getOffsetCount(),
            paginator.getPerPageCount());

        model.addAttribute("users",
                userService.findUsersByIds(ArrayToolkit.column(logs, "userId")));

        model.addAttribute("logs", logs);
        model.addAttribute("paginator", paginator);
        return "/admin/system/logs";
    }
}
