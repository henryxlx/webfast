package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.module.bigapp.service.AppTagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller
public class TagController {

    private final AppTagService tagService;

    public TagController(AppTagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping("/admin/tag")
    public String indexPage(HttpServletRequest request, Model model) {
        int total = tagService.getAllTagCount();
        Paginator paginator = new Paginator(request, total, 20);
        model.addAttribute("tags", tagService.findAllTags(paginator.getOffsetCount(), paginator.getPerPageCount()));
        model.addAttribute("paginator", paginator);
        return "/admin/tag/index";
    }

    @RequestMapping("/admin/tag/create")
    public String createAction(HttpServletRequest request, Model model) {
        if ("POST".equals(request.getMethod())) {
            model.addAttribute("tag",
                    tagService.addTag(AppUser.getCurrentUser(request), ParamMap.toFormDataMap(request)));
            return "/admin/tag/list-tr";
        }

        model.addAttribute("tag", new ParamMap().add("name", "").toMap());
        return "/admin/tag/tag-modal";
    }

    @RequestMapping("/admin/tag/{id}/update")
    public String updateAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        Map<String, Object> tag = tagService.getTag(id);
        if (tag == null || tag.isEmpty()) {
            throw new RuntimeGoingException("标签不存在！");
        }

        if ("POST".equals(request.getMethod())) {
            Map<String, Object> updateMap = ParamMap.toUpdateDataMap(request, tag);
            if (updateMap.size() > 0) {
                tag = tagService.updateTag(AppUser.getCurrentUser(request), id, updateMap);
            }
            model.addAttribute("tag", tag);
            return "/admin/tag/list-tr";
        }

        model.addAttribute("tag", tag);
        return "/admin/tag/tag-modal";
    }

    @RequestMapping("/admin/tag/{id}/delete")
    @ResponseBody
    public Boolean deleteAction(@PathVariable Integer id, HttpServletRequest request) {
        int nums = tagService.deleteTag(AppUser.getCurrentUser(request), id);
        return nums > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @RequestMapping("/admin/tag/checkname")
    @ResponseBody
    public Map<String, Object> checkNameAction(HttpServletRequest request) {
        String name = request.getParameter("value");
        String exclude = request.getParameter("exclude");

        boolean available = tagService.isTagNameAvailable(name, exclude);
        return available ? new ParamMap().add("success", Boolean.TRUE).add("message", "").toMap() :
                new ParamMap().add("success", Boolean.FALSE).add("message", "标签已存在").toMap();
    }
}
