package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.*;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.service.AppLogService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import com.jetwinner.webfast.mvc.viewobj.TimeRange;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author xulixin
 */
@Controller("webfastAdminAnalysisController")
public class AnalysisController {

    private final AppUserService userService;
    private final AppLogService logService;

    public AnalysisController(AppUserService userService, AppLogService logService) {
        this.userService = userService;
        this.logService = logService;
    }

    @RequestMapping("/admin/operation/analysis/routeByAnalysisDateType/{tab}")
    public void routeByAnalysisDateTypeAction(@PathVariable String tab, @RequestParam String analysisDateType,
                                              HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/admin/operation/analysis/" + analysisDateType + "/" + tab;
        request.getRequestDispatcher(url).forward(request, response);
    }

    @RequestMapping("/admin/operation/analysis/register/{tab}")
    public String registerPage(@PathVariable String tab, HttpServletRequest request, Model model) {
        Map<String, Object> condition = ParamMap.toFormDataMap(request);
        TimeRange timeRange = this.getTimeRange(condition);
        if (timeRange == null) {
            BaseControllerHelper.setFlashMessage("danger", "输入的日期有误!", request.getSession());
            return "redirect:/admin/operation/analysis/register/" + tab;
        }
        Paginator paginator = new Paginator(request,
                userService.searchUserCount(timeRange.toMap()),
                20);

        model.addAttribute("registerDetail", userService.searchUsers(timeRange.toMap(),
                OrderBy.build(1).addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount())
        );

        if ("trend".equals(tab)) {
            List<Map<String, Object>> registerData =
                    userService.analysisRegisterDataByTime(timeRange.getStartTime(), timeRange.getEndTime());
            model.addAttribute("chartData", this.fillAnalysisData(condition, registerData));
        }

        String registerStartData = "";

        List<AppUser> users = userService.searchUsers(MapUtil.newHashMap(0),
                OrderBy.build(1).addAsc("createdTime"), 0, 1);
        if (users != null && users.size() > 0) {
            registerStartData = FastTimeUtil.timeToDateStr("yyyy-MM-dd", users.get(0).getCreatedTime());
        }
        model.addAttribute("registerStartDate", registerStartData);

        model.addAttribute("dataInfo", this.getDataInfo(condition, timeRange));
        model.addAttribute("paginator", paginator);
        model.addAttribute("tab", tab);
        return "/admin/operation/analysis/register";
    }

    private TimeRange getTimeRange(Map<String, Object> fields) {
        if (EasyStringUtil.isNotBlank(fields.get("startTime")) && EasyStringUtil.isNotBlank(fields.get("endTime"))) {
            long startTime = FastTimeUtil.dateStrToLong(fields.get("startTime"));
            long endTime = FastTimeUtil.dateStrToLong(fields.get("endTime"));
            if (startTime > endTime) {
                return null;
            }
            return TimeRange.create(startTime, endTime + FastTimeUtil.ONE_DAY_MILLIS);
        }
        return TimeRange.create(FastTimeUtil.timeForFirstDayOfMonth(FastTimeUtil.now()),
                FastTimeUtil.timeForTheNextDay(FastTimeUtil.now()));
    }

    private List<String> makeDateRange(long startTime, long endTime) {
        List<String> dates = new ArrayList<>();

        long currentTime = startTime;
        while (true) {
            if (currentTime > endTime) {
                break;
            }
            String currentDate = FastTimeUtil.timeToDateStr("yyyy-MM-dd", currentTime);
            dates.add(currentDate);

            currentTime = currentTime + FastTimeUtil.ONE_DAY_MILLIS;
        }
        return dates;
    }

    private List<String> getDatesByCondition(Map<String, Object> condition) {
        TimeRange timeRange = this.getTimeRange(condition);
        return this.makeDateRange(timeRange.getStartTime(), timeRange.getEndTime() - FastTimeUtil.ONE_DAY_MILLIS);
    }

    private Map<String, Object> getDataInfo(Map<String, Object> condition, TimeRange timeRange) {
        return new ParamMap()
                .add("startTime", FastTimeUtil.timeToDateStr("yyyy-MM-dd", timeRange.getStartTime()))
                .add("endTime", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        timeRange.getEndTime() - FastTimeUtil.ONE_DAY_MILLIS))
                .add("currentMonthStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                                FastTimeUtil.timeForFirstDayOfMonth(FastTimeUtil.now())))
                .add("currentMonthEnd", FastTimeUtil.timeToDateStr("yyyy-MM-dd", FastTimeUtil.now()))
                .add("lastMonthStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        FastTimeUtil.timeForDayOfLastMonth(FastTimeUtil.now())))
                .add("lastMonthEnd", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                                FastTimeUtil.timeForTheNextDay(FastTimeUtil.now())))
                .add("lastThreeMonthsStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        FastTimeUtil.timeForDayOfPreviousMonth(FastTimeUtil.now(), 3)))
                .add("lastThreeMonthsEnd", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        FastTimeUtil.now()))
                .add("analysisDateType", condition.get("analysisDateType")).toMap();
    }

    private String fillAnalysisData(Map<String, Object> condition, List<Map<String, Object>> currentData) {
        List<String> dates = this.getDatesByCondition(condition);

        List<Map<String, Object>> zeroData = new ArrayList<>();
        for (String value : dates) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("date", value);
            map.put("count", 0);
            zeroData.add(map);
        }

        Map<String, Map<String, Object>> currentDataMap = ArrayToolkit.index(currentData, "date");

        Map<String, Map<String, Object>> zeroDataMap = ArrayToolkit.index(zeroData, "date");

        zeroDataMap.putAll(currentDataMap);
        return JsonUtil.objectToString(zeroDataMap.values());
    }

    @RequestMapping("/admin/operation/analysis/user-sum/{tab}")
    public String userSumAction(@PathVariable String tab, HttpServletRequest request, Model model) {
        Map<String, Object> condition = ParamMap.toFormDataMap(request);
        TimeRange timeRange = this.getTimeRange(condition);
        if (timeRange == null) {
            BaseControllerHelper.setFlashMessage("danger", "输入的日期有误!", request.getSession());
            return "redirect:/admin/operation/analysis/user-sum/" + tab;
        }

        Map<String, Object> result = new ParamMap().add("tab", tab).toMap();
        if ("trend".equals(tab)) {
            List<Map<String, Object>> userSumData = userService.analysisUserSumByTime(timeRange.getEndTime());
            result.put("chartData", this.fillAnalysisUserSum(condition, userSumData));
        } else {
            Paginator paginator = new Paginator(request,
                    userService.searchUserCount(timeRange.toMap()),
                    20);

            List<AppUser> userSumDetail = userService.searchUsers(timeRange.toMap(),
                    OrderBy.build(1).addDesc("createdTime"),
                    paginator.getOffsetCount(),
                    paginator.getPerPageCount());
            result.put("userSumDetail", userSumDetail);
            result.put("paginator", paginator);
        }

        List<AppUser> userSumStartData = userService.searchUsers(MapUtil.newHashMap(0),
                OrderBy.build(1).addAsc("createdTime"), 0, 1);

        if (userSumStartData != null && userSumStartData.size() > 0) {
            result.put("userSumStartDate", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                    userSumStartData.get(0).getCreatedTime()));
        }
        result.put("dataInfo", this.getDataInfo(condition, timeRange));

        model.addAllAttributes(result);
        return "/admin/operation/analysis/user-sum";
    }

    private String fillAnalysisUserSum(Map<String, Object> condition, List<Map<String, Object>>currentData) {
        List<String> dates= this.getDatesByCondition(condition);
        Map<String, Map<String, Object>> currentDataMap = ArrayToolkit.index(currentData, "date");
        TimeRange timeRange = this.getTimeRange(condition);

        List<Map<String, Object>> zeroData = new ArrayList<>();
        for (String value : dates) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("date", value);
            map.put("count", 0);
            zeroData.add(map);
        }

        List<Map<String, Object>> userSumData = userService.analysisUserSumByTime(timeRange.getEndTime());
        if (userSumData != null && userSumData.size() > 0){
            Object countTmp = userSumData.get(0).get("count");
            for (Map<String, Object> value : zeroData) {
                Object date = value.get("date");
                if (currentData.contains(date)) {
                    value.put("count", currentDataMap.get(date).get("count"));
                    countTmp = currentDataMap.get(date).get("count");
                } else {
                    value.put("count", countTmp);
                }
            }
        }

        return JsonUtil.objectToString(zeroData);
    }

    @RequestMapping("/admin/operation/analysis/login/{tab}")
    public String loginAction(@PathVariable String tab, HttpServletRequest request, Model model) {
        Map<String, Object> condition = ParamMap.toFormDataMap(request);
        TimeRange timeRange = this.getTimeRange(condition);
        if (timeRange == null) {
            BaseControllerHelper.setFlashMessage("danger", "输入的日期有误!", request.getSession());
            return "redirect:/admin/operation/analysis/login/" + tab;
        }

        Paginator paginator = new Paginator(request,
                logService.searchLogCount(new ParamMap().add("action", "login_success")
                        .add("startDateTime", timeRange.getStartTime())
                        .add("endDateTime", timeRange.getEndTime()).toMap()),
                20);

        List<Map<String, Object>> loginDetail = logService.searchLogs(
                new ParamMap().add("action", "login_success")
                        .add("startDateTime", timeRange.getStartTime())
                        .add("endDateTime", timeRange.getEndTime()).toMap(),
                "created",
                paginator.getOffsetCount(),
                paginator.getPerPageCount());


        if ("trend".equals(tab)) {
            List<Map<String, Object>> LoginData = logService.analysisLoginDataByTime(timeRange.getStartTime(),
                    timeRange.getEndTime());

            model.addAttribute("chartData", this.fillAnalysisData(condition, LoginData));
        }

        Set<Object> userIds = ArrayToolkit.column(loginDetail, "userId");
        model.addAttribute("users", userService.findUsersByIds(userIds));

        List<Map<String, Object>> loginStartData = logService.searchLogs(
                new ParamMap().add("action", "login_success").toMap(), "createdByAsc", 0, 1);

        if (loginStartData != null && loginStartData.size() > 0) {
            model.addAttribute("loginStartDate", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                    ValueParser.parseLong(loginStartData.get(0).get("createdTime"))));
        }

        model.addAttribute("dataInfo", this.getDataInfo(condition, timeRange));
        model.addAttribute("loginDetail", loginDetail);
        model.addAttribute("paginator", paginator);
        model.addAttribute("tab", tab);
        return "/admin/operation/analysis/login";
    }
}
