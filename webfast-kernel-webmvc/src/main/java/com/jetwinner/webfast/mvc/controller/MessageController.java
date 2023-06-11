package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessage;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.service.AppMessageService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.kernel.view.ViewRenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author libingquan
 */
@Controller("webfastSiteMessageController")
public class MessageController {

    private final AppUserService userService;
    private final AppMessageService messageService;
    private final ViewRenderService viewRenderService;

    public MessageController(AppUserService userService,
                             AppMessageService messageService,
                             ViewRenderService viewRenderService) {

        this.userService = userService;
        this.messageService = messageService;
        this.viewRenderService = viewRenderService;
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
        String username = request.getParameter("message[receiver]");
        String messageContent = request.getParameter("message[content]");
        AppUser receiver = userService.getUserByUsername(username);
        if (receiver == null) {
            throw new RuntimeGoingException("抱歉，该收信人尚未注册!");
        }
        messageService.sendMessage(user.getId(), receiver.getId(), messageContent);
        return "redirect:/message";
    }

    @GetMapping("/message/conversation/{conversationId}")
    public String showConversationPage(@PathVariable Integer conversationId,
                                       HttpServletRequest request,
                                       Model model) {

        AppUser user = AppUser.getCurrentUser(request);
        AppModelMessageConversation conversation = messageService.getConversation(conversationId);
        if (conversation == null || !Objects.equals(conversation.getToId(), user.getId())) {
            throw new RuntimeGoingException("私信会话不存在！");
        }
        Paginator paginator = new Paginator(request,
                messageService.getConversationMessageCount(conversationId), 10);

        messageService.markConversationRead(conversationId);

        model.addAttribute("messages", messageService.findConversationMessages(
                conversation.getId(),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()));

        model.addAttribute("conversation", conversation);
        model.addAttribute("receiver", userService.getUser(conversation.getFromId()));
        model.addAttribute("paginator", paginator);
        return "/message/conversation-show";
    }

    @PostMapping("/message/conversation/{conversationId}")
    @ResponseBody
    public Map<String, Object> showConversationAction(@PathVariable Integer conversationId,
                                                      HttpServletRequest request) {

        AppUser user = AppUser.getCurrentUser(request);
        AppModelMessageConversation conversation = messageService.getConversation(conversationId);
        if (conversation == null || !Objects.equals(conversation.getToId(), user.getId())) {
            throw new RuntimeGoingException("私信会话不存在！");
        }
        Map<String, Object> fields = ParamMap.toFormDataMap(request);
        AppModelMessage message = this.messageService.sendMessage(user.getId(), conversation.getFromId(), fields.get("content"));
        String html = viewRenderService.renderView(request, "/message/item.ftl",
                new ParamMap().add("ctx", request.getContextPath()).add("appUser", user)
                        .add("message", message).add("conversation", conversation).toMap());
        return new ParamMap().add("status", "ok").add("html", html).toMap();
    }

    @RequestMapping("/message/check/receiver")
    @ResponseBody
    public Map<String, Object> checkReceiverAction(String value, HttpServletRequest request) {
        AppUser currentUser = AppUser.getCurrentUser(request);
        String username = value;
        boolean result = userService.isUsernameAvailable(username);
        ParamMap paramMap = new ParamMap();
        if (!result) {
            paramMap.add("success", Boolean.FALSE).add("message", "该收件人不存在");
        } else if (currentUser.getUsername().equals(username)) {
            paramMap.add("success", Boolean.FALSE).add("message", "不能给自己发私信哦！");
        } else {
            paramMap.add("success", Boolean.TRUE).add("message", "");
        }
        return paramMap.toMap();
    }

    @RequestMapping("/message/conversation/{conversationId}/delete")
    public String deleteConversationAction(@PathVariable Integer conversationId, HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        AppModelMessageConversation conversation = messageService.getConversation(conversationId);
        if (conversation == null || !Objects.equals(conversation.getToId(), user.getId())) {
            throw new RuntimeGoingException("您无权删除此私信！");
        }

        messageService.deleteConversation(conversationId);
        return "redirect:/message";
    }

    @RequestMapping("/message/conversation/{conversationId}/message/{messageId}/delete")
    public String deleteConversationMessageAction(@PathVariable Integer conversationId,
                                                  @PathVariable Integer messageId, HttpServletRequest request) {

        AppUser user = AppUser.getCurrentUser(request);
        AppModelMessageConversation conversation = messageService.getConversation(conversationId);
        if (conversation == null || !Objects.equals(conversation.getToId(), user.getId())) {
            throw new RuntimeGoingException("您无权删除此私信！");
        }

        messageService.deleteConversationMessage(conversationId, messageId);
        int messagesCount = messageService.getConversationMessageCount(conversationId);
        if (messagesCount > 0) {
            return "redirect:/message/conversation/" + conversationId;
        } else {
            return "redirect:/message";
        }
    }

    @RequestMapping("/following/bynickname/match_jsonp")
    @ResponseBody
    public List<Map<String, Object>> matchAction(HttpServletRequest request) {
        Integer currentUserId = AppUser.getCurrentUser(request).getId();
        List<Map<String, Object>> data = new ArrayList<>();
        String queryString = request.getParameter("q");
        String callback = request.getParameter("callback");
        List<AppUser> findedUsersByNickname = userService.searchUsers(
                new ParamMap().add("username", queryString).toMap(),
                OrderBy.build(1).addDesc("registerDate"), 0, 10);
        Set<Object> findedFollowingIds = userService.filterFollowingIds(currentUserId,
                ArrayToolkitOnJava8.column(findedUsersByNickname, AppUser::getId));

        Map<String, AppUser> filterFollowingUsers = userService.findUsersByIds(findedFollowingIds);

        for (AppUser filterFollowUser : filterFollowingUsers.values()) {
            data.add(new ParamMap()
                    .add("id", filterFollowUser.getId())
                    .add("username", filterFollowUser.getUsername()).toMap());
        }

        return data;
    }


    @RequestMapping("/message/create/{toId}")
    public String createAction(@PathVariable Integer toId, HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        AppUser receiver = this.userService.getUser(toId);
        if ("POST".equals(request.getMethod())) {
            Map<String, Object> message = ParamMap.toFormDataMap(request);
            String username = String.valueOf(message.get("message[receiver]"));
            receiver = this.userService.getUserByUsername(username);
            if (receiver == null) {
                throw new RuntimeGoingException("抱歉，该收信人尚未注册!");
            }
            this.messageService.sendMessage(user.getId(), receiver.getId(), message.get("message[content]"));
            return "redirect:/message";
        }
        model.addAttribute("userId", toId);
        model.addAttribute("receiver", receiver.getUsername());
        return "/message/send-message-modal";
    }

}
