package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.FastDirectoryUtil;
import com.jetwinner.webfast.kernel.FastAppConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xulixin
 */
@Controller("webfastAdminOptimizeController")
public class OptimizeController {

    private final FastAppConst appConst;

    public OptimizeController(FastAppConst appConst) {
        this.appConst = appConst;
    }

    @RequestMapping("/admin/optimize/remove-tmp")
    @ResponseBody
    public Boolean removeTempDirAction() {
        if(!this.isDisabledUpgrade()){
            FastDirectoryUtil.emptyDir(appConst.getDownloadPath());
        }
        FastDirectoryUtil.emptyDir(appConst.getUploadTmpPath());
        return Boolean.TRUE;
    }

    private boolean isDisabledUpgrade() {
        return true;
    }
}
