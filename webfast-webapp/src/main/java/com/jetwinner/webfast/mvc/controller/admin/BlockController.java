package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.ArrayToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.JsonUtil;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppBlockService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminBlockController")
public class BlockController {

    private final AppBlockService blockService;
    private final AppUserService userService;

    public BlockController(AppBlockService blockService, AppUserService userService) {
        this.blockService = blockService;
        this.userService = userService;
    }

    @RequestMapping("/admin/block")
    public String indexPage(HttpServletRequest request, Model model) {
        Paginator paginator = new Paginator(request, blockService.searchBlockCount(), 20);
        model.addAttribute("blocks", blockService.searchBlocks(paginator.getOffsetCount(),
                paginator.getPerPageCount()));

        Map<String, Object> latestBlockHistory = blockService.getLatestBlockHistory();
        model.addAttribute("latestBlockHistory", latestBlockHistory);
        model.addAttribute("latestUpdateUser", userService.getUser(latestBlockHistory.get("userId")));
        model.addAttribute("paginator", paginator);
        return "/admin/block/index";
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
        model.addAttribute("paginator", paginator);
        return "/admin/block/block-update-modal";
    }

    @GetMapping("admin/blockhistory/{id}/preview")
    public String previewPage(Integer id, Model model) {
        model.addAttribute("blockHistory", blockService.getBlockHistory(id));
        return "/admin/block/blockhistory-preview";
    }
}
