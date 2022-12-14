package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.ArrayToolkitOnJava8;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.service.AppMessageService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
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
        String username = request.getParameter("message[receiver]");
        String messageContent = request.getParameter("message[content]");
        AppUser receiver = userService.getUserByUsername(username);
        if (receiver == null) {
            throw new RuntimeGoingException("?????????????????????????????????!");
        }
        messageService.sendMessage(user.getId(), receiver.getId(), messageContent);
        return "redirect:/message";
    }

    @GetMapping("/message/conversation/{conversationId}/show")
    public String showConversationAction(@PathVariable Integer conversationId, HttpServletRequest request,
                                         Model model) throws Exception {

        AppUser user = AppUser.getCurrentUser(request);
        AppModelMessageConversation conversation = messageService.getConversation(conversationId);
        if (conversation == null || !Objects.equals(conversation.getToId(), user.getId())) {
            throw new RuntimeGoingException("????????????????????????");
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

    @RequestMapping("/message/check/receiver")
    @ResponseBody
    public Map<String, Object> checkReceiverAction(String value, HttpServletRequest request) {
        AppUser currentUser = AppUser.getCurrentUser(request);
        String username = value;
        boolean result = userService.isUsernameAvailable(username);
        ParamMap paramMap = new ParamMap();
        if (!result) {
            paramMap.add("success", Boolean.FALSE).add("message", "?????????????????????");
        } else if (currentUser.getUsername().equals(username)) {
            paramMap.add("success", Boolean.FALSE).add("message", "??????????????????????????????");
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
            throw new RuntimeGoingException("???????????????????????????");
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
            throw new RuntimeGoingException("???????????????????????????");
        }

        messageService.deleteConversationMessage(conversationId, messageId);
        int messagesCount = messageService.getConversationMessageCount(conversationId);
        if (messagesCount > 0) {
            return "redirect:/message/conversation/" + conversationId + "/show";
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

}
