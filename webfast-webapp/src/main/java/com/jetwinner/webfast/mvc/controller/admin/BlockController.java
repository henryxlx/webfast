package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.service.AppBlockService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
}
