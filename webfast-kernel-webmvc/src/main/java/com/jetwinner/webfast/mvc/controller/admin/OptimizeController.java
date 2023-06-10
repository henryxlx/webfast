package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.util.FastDirectoryUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.webfast.database.FastDatabaseUtil;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
        if (!this.isDisabledUpgrade()) {
            FastDirectoryUtil.emptyDir(appConst.getDownloadPath());
        }
        FastDirectoryUtil.emptyDir(appConst.getUploadTmpPath());
        return Boolean.TRUE;
    }

    @RequestMapping("/admin/optimize/backupdb")
    @ResponseBody
    public Map<String, Object> backupDbAction(HttpServletRequest request) {
        String db = FastDatabaseUtil.backupdb(appConst.getUploadTmpPath());
        String downloadFile = request.getContextPath() + "/files/" + PhpStringUtil.basename(db);
        return new ParamMap().add("status", "ok").add("result", downloadFile).toMap();
    }

    private boolean isDisabledUpgrade() {
        return true;
    }
}
