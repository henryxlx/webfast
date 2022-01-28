package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import com.jetwinner.webfast.kernel.service.AppMessageService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("adminMessageController")
public class MessageController {

    private final AppMessageService messageService;
    private final AppUserService userService;

    public MessageController(AppMessageService messageService, AppUserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/admin/message")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> fields = ParamMap.toConditionMap(request);
        Map<String, Object> conditions = new ParamMap()
                .add("startDate", 0)
                .add("endDate", System.currentTimeMillis()).toMap();

        if(fields != null && fields.size() > 0){
            conditions = convertConditions(fields);
        }

        Paginator paginator = new Paginator(request,
                messageService.searchMessagesCount(conditions),
                20);

        List<AppModelMessage> messages = messageService.searchMessages(conditions, null,
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        Map<String, AppUser> users = userService.findUsersByIds(ArrayToolkitOnJava8.column(messages, AppModelMessage::getFromId));
        Map<String, AppUser> usersToId = userService.findUsersByIds(ArrayToolkitOnJava8.column(messages, AppModelMessage::getToId));
        users.putAll(usersToId);
        model.addAttribute("users", users);
        model.addAttribute("messages", messages);
        model.addAttribute("paginator", paginator);
        return "/admin/message/index";
    }

    private Map<String, Object> convertConditions(Map<String, Object> conditions) {
        if (EasyStringUtil.isNotBlank(conditions.get("username"))) {
            AppUser user = userService.getUserByUsername(String.valueOf(conditions.get("username")));
            if (user == null) {
                throw new RuntimeGoingException(String.format("昵称为%s的用户不存在", conditions.get("username")));
            }
            conditions.put("fromId", user.getId());
        }
        conditions.remove("username");

        if(EasyStringUtil.isBlank(conditions.get("content"))) {
            conditions.remove("content");
        }

        if(EasyStringUtil.isBlank(conditions.get("startDate"))){
            conditions.remove("startDate");
        }

        if(EasyStringUtil.isBlank(conditions.get("endDate"))){
            conditions.remove("endDate");
        }

        if(conditions.containsKey("startDate")){
            conditions.put("startDate", conditions.get("startDate"));
        }

        if(conditions.containsKey("endDate")){
            conditions.put("endDate", conditions.get("endDate"));
        }

        return conditions;
    }
}
