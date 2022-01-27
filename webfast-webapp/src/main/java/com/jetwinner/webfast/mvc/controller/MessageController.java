package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.service.AppMessageService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.view.PostDataMapForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author libingquan
 */
@Controller("webfastSiteMessageController")
public class MessageController {

    private final AppUserService userService;
    private final AppMessageService messageService;

    public MessageController(AppUserService userService, AppMessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @RequestMapping("/message")
    public String indexPage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);

        Paginator paginator = new Paginator(request,
                messageService.getUserConversationCount(user.getId()), 10);

        List<AppModelMessageConversation> conversations = messageService.findUserConversations(
                user.getId(),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()
        );

        messageService.clearUserNewMessageCounter(user.getId());
        model.addAttribute("users", userService.findUsersByIds(ArrayToolkitOnJava8.column(conversations,
                AppModelMessageConversation::getFromId)));
        model.addAttribute("conversations", conversations);
        model.addAttribute("paginator", paginator);
        return "/message/index";
    }

    @GetMapping("/message/send")
    public String sendPage() {
        return "/message/create";
    }

    @PostMapping("/message/send")
    public String sendAction(HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        PostDataMapForm form = PostDataMapForm.createForm("", "");
        form.bind(request);
        if (form.isValid()) {
            Map<String, Object> message = form.getData();
            String username = String.valueOf(message.get("receiver"));
            AppUser receiver = userService.getUserByUsername(username);
            if (receiver == null) {
                throw new RuntimeGoingException("抱歉，该收信人尚未注册!");
            }
            messageService.sendMessage(user.getId(), receiver.getId(), message.get("content"));
        }
        return "redirect:/message";
    }
}
