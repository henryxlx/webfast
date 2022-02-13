package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.toolbag.ConvertIpToolkit;
import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.FastTimeUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.AppLogDao;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author xulixin
 */
@Controller("adminLoginRecordController")
public class LoginRecordController {

    private final AppLogDao logDao;
    private final AppUserService userService;

    public LoginRecordController(AppLogDao logDao, AppUserService userService) {
        this.logDao = logDao;
        this.userService = userService;
    }

    @GetMapping("/admin/login-record")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ParamMap.toConditionMap(request);
        Map<String, Object> userConditions = new HashMap<>(2);
        if (EasyStringUtil.isNotBlank(conditions.get("keywordType"))) {
            userConditions.put("keywordType", conditions.get("keywordType"));
        }

        if (EasyStringUtil.isNotBlank(conditions.get("keyword"))) {
            userConditions.put("keyword", conditions.get("keyword"));
        }

        if(userConditions.containsKey("keywordType") && userConditions.containsKey("keyword")) {
            List<AppUser> users = userService.searchUsers(userConditions, OrderBy.builder().addDesc("createdTime"),0,2000);
            Set<Object> userIds = ArrayToolkitOnJava8.column(users, AppUser::getId);
            if(userIds != null && userIds.size() > 0){
                conditions.put("userIds", userIds);
                conditions.remove("username");
            } else {
                Paginator paginator = new Paginator(request, 0, 20);
                model.addAttribute("logRecords", new ArrayList<Map<String, Object>>(0));
                model.addAttribute("users", new HashMap<String, Map<String, Object>>(0));
                model.addAttribute("paginator", paginator);
                return "/admin/login-record/index";
            }
        }

        conditions.put("action", "login_success");

        prepareSearchConditions(conditions);
        Paginator paginator = new Paginator(request,
                logDao.searchLogCount(conditions),
                20);

        List<Map<String, Object>> logRecords = logDao.searchLogs(
                conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        logRecords.forEach(log -> log.put("ip", ConvertIpToolkit.convertIp(String.valueOf(log.get("ip")))));
        model.addAttribute("logRecords", logRecords);
        Set<Object> userIds = ArrayToolkit.column(logRecords, "userId");
        model.addAttribute("users", userService.findUsersByIds(userIds));
        model.addAttribute("paginator", paginator);
        return "/admin/login-record/index";
    }

    private void prepareSearchConditions(Map<String, Object> conditions) {
        if (EasyStringUtil.isNotBlank(conditions.get("username"))) {
            AppUser existsUser = userService.getUserByUsername(String.valueOf(conditions.get("username")));
            Integer userId = existsUser != null && existsUser.getId() != null ? existsUser.getId() : -1;
            conditions.put("userId", userId);
            conditions.remove("username");
        }

        if (EasyStringUtil.isNotBlank(conditions.get("startDateTime")) &&
                EasyStringUtil.isNotBlank(conditions.get("endDateTime"))) {

            conditions.put("startDateTime", FastTimeUtil.dateStrToLong(conditions.get("startDateTime")));
            conditions.put("endDateTime", FastTimeUtil.dateStrToLong(conditions.get("endDateTime")));
        } else {
            conditions.remove("startDateTime");
            conditions.remove("endDateTime");
        }

        if (EasyStringUtil.isNotBlank(conditions.get("level")) &&
                ArrayUtil.contains(new Object[]{"info", "warning", "error"}, conditions.get("level"))) {

            conditions.put("level", conditions.get("level"));
        } else {
            conditions.remove("level");
        }
    }

    @GetMapping("/admin/login-record/{id}/details")
    public String showUserLoginRecordPage(@PathVariable Integer id, HttpServletRequest request, Model model) {
        AppUser user = userService.getUser(id);

        Map<String, Object> conditions = new ParamMap().add("userId", user.getId()).toMap();
        Paginator paginator = new Paginator(request,
                logDao.searchLogCount(conditions), 8);

        List<Map<String, Object>> loginRecords = logDao.searchLogs(conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        loginRecords.forEach(log -> log.put("ip", ConvertIpToolkit.convertIp(String.valueOf(log.get("ip")))));
        model.addAttribute("user", user);
        model.addAttribute("loginRecords", loginRecords);
        model.addAttribute("loginRecordPaginator", paginator);

        return "/admin/login-record/details";
    }
}
