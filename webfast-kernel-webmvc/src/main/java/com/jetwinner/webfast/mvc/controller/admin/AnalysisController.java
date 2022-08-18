package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.*;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminAnalysisController")
public class AnalysisController {

    private final AppUserService userService;

    public AnalysisController(AppUserService userService) {
        this.userService = userService;
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
            return "redirect:/admin/operation/analysis/register/trend";
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
            model.addAttribute("data", this.fillAnalysisData(condition, registerData));
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
            long startTime = ValueParser.parseLong(fields.get("startTime"));
            long endTime = ValueParser.parseLong(fields.get("endTime"));
            if (startTime > endTime) {
                return null;
            }
            return TimeRange.create(FastTimeUtil.dateStrToLong(fields.get("startTime")),
                    FastTimeUtil.dateStrToLong(fields.get("endTime")) + 24 * 3600);
        }
        return TimeRange.create(FastTimeUtil.timeForFirstDayOfMonth(FastTimeUtil.now()),
                FastTimeUtil.startTimeOfTheNextDay(FastTimeUtil.now()));
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

            currentTime = currentTime + 3600 * 24;
        }
        return dates;
    }

    private List<String> getDatesByCondition(Map<String, Object> condition) {
        TimeRange timeRange = this.getTimeRange(condition);
        return this.makeDateRange(timeRange.getStartTime(), timeRange.getEndTime() - 24 * 3600);
    }

    private Map<String, Object> getDataInfo(Map<String, Object> condition, TimeRange timeRange) {
        return new ParamMap()
                .add("startTime", FastTimeUtil.timeToDateStr("yyyy-MM-dd", timeRange.getStartTime()))
                .add("endTime", FastTimeUtil.timeToDateStr("yyyy-MM-dd", timeRange.getEndTime() - 24 * 3600))
                .add("currentMonthStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                                FastTimeUtil.timeForFirstDayOfMonth(FastTimeUtil.now())))
                .add("currentMonthEnd", FastTimeUtil.timeToDateStr("yyyy-MM-dd", FastTimeUtil.now()))
                .add("lastMonthStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        FastTimeUtil.startTimeOfDayLastMonth(FastTimeUtil.now())))
                .add("lastMonthEnd", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                                FastTimeUtil.startTimeOfTheNextDay(FastTimeUtil.now())))
                .add("lastThreeMonthsStart", FastTimeUtil.timeToDateStr("yyyy-MM-dd",
                        FastTimeUtil.startTimeOfDayLastFewMonth(FastTimeUtil.now(), 3)))
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

    @RequestMapping("/admin/operation/analysis/login/{tab}")
    public String loginPage(@PathVariable String tab) {
        return "/admin/operation/analysis/login";
    }

    @RequestMapping("/admin/operation/analysis/user-sum/{tab}")
    public String userSumPage(@PathVariable String tab) {
        return "/admin/operation/analysis/user-sum";
    }

}
