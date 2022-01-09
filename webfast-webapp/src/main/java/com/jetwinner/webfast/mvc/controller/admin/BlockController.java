package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppBlockService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.service.ViewRenderService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminBlockController")
public class BlockController {

    private final AppBlockService blockService;
    private final AppUserService userService;
    private final ViewRenderService viewRenderService;

    public BlockController(AppBlockService blockService,
                           AppUserService userService,
                           ViewRenderService viewRenderService) {

        this.blockService = blockService;
        this.userService = userService;
        this.viewRenderService = viewRenderService;
    }

    @RequestMapping("/admin/block")
    public String indexPage(HttpServletRequest request, Model model) {
        Paginator paginator = new Paginator(request, blockService.searchBlockCount(), 20);
        model.addAttribute("blocks", blockService.searchBlocks(paginator.getOffsetCount(),
                paginator.getPerPageCount()));

        Map<String, Object> latestBlockHistory = blockService.getLatestBlockHistory();
        model.addAttribute("latestBlockHistory", latestBlockHistory);
        model.addAttribute("latestUpdateUser", userService.getUser(latestBlockHistory.get("userId")));
        model.addAttribute(Paginator.MODEL_ATTR_NAME, paginator);
        return "/admin/block/index";
    }

    @GetMapping("admin/blockhistory/{id}/preview")
    public String previewPage(@PathVariable Integer id, Model model) {
        model.addAttribute("blockHistory", blockService.getBlockHistory(id));
        return "/admin/block/blockhistory-preview";
    }

    @GetMapping("/admin/block/{blockId}/update")
    public String updatePage(@PathVariable String blockId, HttpServletRequest request, Model model) {
        Map<String, Object> block = EasyStringUtil.isNumeric(blockId) ?
                blockService.getBlock(blockId) : blockService.getBlockByCode(blockId);

        Paginator paginator = new Paginator(request,
                blockService.countBlockHistoryByBlockId(block.get("id")),
                5);

        if ("template".equals(block.get("mode"))) {
            model.addAttribute("templateItems", blockService.generateBlockTemplateItems(block));
            model.addAttribute("templateData", JsonUtil.jsonDecode(block.get("templateData"), true));
        }

        List<Map<String, Object>> blockHistories = blockService.findBlockHistoriesByBlockId(block.get("id"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount());

        for (Map<String, Object> blockHistory : blockHistories) {
            blockHistory.put("templateData", JsonUtil.jsonDecode(blockHistory.get("templateData"), true));
        }
        model.addAttribute("blockHistories", blockHistories);

        model.addAttribute("historyUsers",
                userService.findUsersByIds(ArrayToolkit.column(blockHistories, "userId")));
        model.addAttribute("block", block);
        model.addAttribute(Paginator.MODEL_ATTR_NAME, paginator);
        return "/admin/block/block-update-modal";
    }

    @PostMapping("/admin/block/{blockId}/update")
    @ResponseBody
    public Map<String, Object> updateAction(@PathVariable String blockId, HttpServletRequest request) {
        Map<String, Object> block = EasyStringUtil.isNumeric(blockId) ?
                blockService.getBlock(blockId) : blockService.getBlockByCode(blockId);
        Map<String, Object> fields = ParamMap.toPostDataMap(request);
        if ("template".equals(block.get("mode"))) {
            String template = String.valueOf(block.get("template"));
            // template = str_replace(array("(( "," ))","((  ","  )"),array("((","))","((","))"),$template);

            String content = "";
            for (Map.Entry entry : fields.entrySet()) {
                content = template.replace("((" + entry.getKey() + "))", String.valueOf(entry.getValue()));
                break;
            }
            for (Map.Entry entry : fields.entrySet()) {
                content = content.replace("((" + entry.getKey() + "))", String.valueOf(entry.getValue()));
                break;
            }
            Map<String, Object> templateData = new HashMap<>(fields.size());
            templateData.putAll(fields);
            fields.clear();
            fields.put("content", content);
            fields.put("templateData", JsonUtil.objectToString(templateData));
        }

        block = blockService.updateBlock(AppUser.getCurrentUser(request), blockId, fields);
        Map<String, Object> latestBlockHistory = blockService.getLatestBlockHistory();
        AppUser latestUpdateUser = userService.getUser(latestBlockHistory.get("userId"));
        String html = viewRenderService.renderView("/admin/block/list-tr.ftl",
                new ParamMap().add("block", block).add("ctx", request.getContextPath())
                        .add("latestUpdateUser", latestUpdateUser).toMap(),
                new ParamMap().add("admin_macro", "/admin/admin_macro.ftl").toMap());
        return new ParamMap().add("status", "ok").add("html", html).toMap();
    }

}
