package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("adminUserApprovalController")
public class UserApprovalController {

    private final AppUserService userService;
    private final FastAppConst appConst;
    private final ResourceLoader resourceLoader;

    public UserApprovalController(AppUserService userService, FastAppConst appConst, ResourceLoader resourceLoader) {
        this.userService = userService;
        this.appConst = appConst;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/admin/approval/approving")
    public String approvingPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ParamMap.toConditionMap(request);

        Map<String, Object> conditions = new ParamMap()
                .add("roles", "")
                .add("keywordType", "")
                .add("keyword", "")
                .add("approvalStatus", "approving").toMap();

        ParamMap.mergeConditionMap(conditions, fields);

        Paginator paginator = new Paginator(request, userService.searchUserCount(conditions), 20);

        List<AppUser> users = userService.searchUsers(
                conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        List<Map<String, Object>> approvals = userService.findUserApprovalsByUserIds(ArrayToolkitOnJava8.column(users, AppUser::getId));
        model.addAttribute("approvals", ArrayToolkit.index(approvals, "userId"));
        model.addAttribute("users", users);
        model.addAttribute("paginator", paginator);
        return "/admin/user/approving";
    }

    @GetMapping("/admin/approval/{id}/approve")
    public String approveAction(@PathVariable Integer id, Model model) {
        AppUser user = userService.getUser(id);
        model.addAttribute("userApprovalInfo",
                userService.getLastestApprovalByUserIdAndStatus(user.getId(), "approving"));
        model.addAttribute("user", user);
        return "/admin/user/user-approve-modal";
    }

    @PostMapping("/admin/approval/{id}/approve")
    @ResponseBody
    public Map<String, Object> approveAction(@PathVariable Integer id, HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        Map<String, Object> data = ParamMap.toPostDataMap(request);
        if ("success".equals(data.get("form_status"))) {
            userService.passApproval(id, String.valueOf(data.get("note")), user);
        } else if ("fail".equals(data.get("form_status"))) {
            userService.rejectApproval(id, String.valueOf(data.get("note")), user);
        }
        return new ParamMap().add("status", "ok").toMap();
    }

    @GetMapping("/admin/approval/approved")
    public String approvedPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ParamMap.toConditionMap(request);

        Map<String, Object> conditions = new ParamMap()
                .add("roles", "")
                .add("keywordType", "")
                .add("keyword", "")
                .add("approvalStatus", "approved").toMap();

        ParamMap.mergeConditionMap(conditions, fields);

        Paginator paginator = new Paginator(request, userService.searchUserCount(conditions), 20);

        List<AppUser> users = userService.searchUsers(
                conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        List<Map<String, Object>> userProfiles = userService.findUserProfilesByIds(ArrayToolkitOnJava8.column(users, AppUser::getId));
        model.addAttribute("userProfiles", ArrayToolkit.index(userProfiles, "id"));
        model.addAttribute("users", users);
        model.addAttribute("paginator", paginator);
        return "/admin/user/approved";
    }

    @GetMapping("/admin/approval/{id}/view")
    public String viewApprovalInfoPage(@PathVariable Integer id, Model model){
        AppUser user = userService.getUser(id);

        model.addAttribute("userApprovalInfo",
                userService.getLastestApprovalByUserIdAndStatus(user.getId(), "approving"));
        model.addAttribute("user", user);
        return "/admin/user/user-approve-info-modal";
    }

    @RequestMapping("/admin/user/show-idcard")
    @ResponseBody
    public ResponseEntity<?> showIdCardAction(Integer userId, String type, HttpServletRequest request) {
        AppUser user = userService.getUser(userId);
        AppUser currentUser = AppUser.getCurrentUser(request);

        if (currentUser == null) {
            throw new RuntimeGoingException("无访问权限！");
        }

        Map<String, Object> userApprovalInfo = userService.getLastestApprovalByUserIdAndStatus(user.getId(), "approving");

        Object idcardPath = "back".equals(type) ? userApprovalInfo.get("backImg") : userApprovalInfo.get("faceImg");
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + ""));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/admin/approval/{id}/cancel")
    @ResponseBody
    public Boolean cancelAction(@PathVariable Integer id, HttpServletRequest request) {
        userService.rejectApproval(id, "管理员撤销", AppUser.getCurrentUser(request));
        return Boolean.TRUE;
    }
}
