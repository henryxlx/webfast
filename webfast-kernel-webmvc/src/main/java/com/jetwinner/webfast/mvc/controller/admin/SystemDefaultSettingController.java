package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.toolbag.FileToolkit;
import com.jetwinner.util.FastDirectoryUtil;
import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.image.ImageSize;
import com.jetwinner.webfast.image.ImageUtil;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppPathInfo;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastAdminSystemDefaultSettingController")
public class SystemDefaultSettingController {

    @RequestMapping("/admin/setting/theme")
    public String themePage() {
        return "/admin/system/theme";
    }

    @RequestMapping("/admin/setting/user-center")
    public String userCenterPage() {
        return "/admin/system/user-center";
    }

    @RequestMapping("/admin/setting/admin-sync")
    public String adminSyncPage() {
        return "/admin/system/admin-sync";
    }

    @RequestMapping("/admin/setting/mobile")
    public String mobilePage(Model model) {
        return "/admin/system/mobile";
    }

    @RequestMapping("/admin/setting/ip-blacklist")
    public String ipBlackListPage() {
        return "/admin/system/ip-blacklist";
    }

    @RequestMapping("/admin/optimize")
    public String optimizePage() {
        return "/admin/system/optimize";
    }

    // ----------------------------------------------------------------------------------------------------------------

    private static final String DEFAULT_SETTING_KEY_NAME = "default";

    private final AppSettingService settingService;
    private final FastAppConst appConst;

    public SystemDefaultSettingController(AppSettingService settingService, FastAppConst appConst) {
        this.settingService = settingService;
        this.appConst = appConst;
    }

    @PostMapping("/admin/setting/user-avatar/default")
    public String userAvatarDefaultAction() {
        Map<String, Object> defaultSetting = settingService.get(DEFAULT_SETTING_KEY_NAME);
        defaultSetting.put("defaultAvatar", "0");
        defaultSetting.put("defaultAvatarFileName", "");
        settingService.set(DEFAULT_SETTING_KEY_NAME, defaultSetting);
        return "redirect:/admin/setting/user-avatar";
    }

    @GetMapping("/admin/setting/user-avatar")
    public String userAvatarPage(Model model) {
        Map<String, Object> defaultSetting = settingService.get(DEFAULT_SETTING_KEY_NAME);
        if (!defaultSetting.containsKey("defaultAvatar")) {
            defaultSetting.put("defaultAvatar", 0);
        }
        model.addAttribute("defaultSetting", defaultSetting);
        return "/admin/system/user-avatar";
    }

    @PostMapping("/admin/setting/user-avatar")
    public ModelAndView defaultAvatarAction(@RequestParam(value = "picture", required = false) MultipartFile file) {
        if (file == null) {
            return BaseControllerHelper.createMessageResponse("error", "没有可供上传的文件，请选择文件。");
        }
        if (!FileToolkit.isImageFile(file.getOriginalFilename())) {
            return BaseControllerHelper.createMessageResponse("error", "上传图片格式错误，请上传jpg, gif, png格式的文件。");
        }

        ModelAndView mav = new ModelAndView("/admin/system/user-avatar-crop");
        String filenamePrefix = "avatar";
        String hash = FileToolkit.hashFilename(filenamePrefix);
        String ext = FileToolkit.getFileExtension(file.getOriginalFilename());
        String filename = filenamePrefix + hash + "." + ext;

        Map<String, Object> defaultSetting = settingService.get(DEFAULT_SETTING_KEY_NAME);
        defaultSetting.put("defaultAvatarFileName", filename);
        settingService.set(DEFAULT_SETTING_KEY_NAME, defaultSetting);

        String directory = appConst.getUploadPublicDirectory() + "/tmp/";
        try {
            FastDirectoryUtil.dirNotExistsThenMake(directory);
            file.transferTo(new File(directory + filename));
        } catch (IOException e) {
            return BaseControllerHelper.createMessageResponse("error",
                    String.format("图片上传失败，请检查上传目录(%s)或文件(%s)是否存在。", directory, filename));
        }

        String pictureFilePath = directory + "/" + filename;

        ImageSize imageSize = null;
        try {
            imageSize = ImageUtil.getNaturalSize(new File(pictureFilePath));
        } catch (Exception e) {
            return BaseControllerHelper.createMessageResponse("error", "获取上传图片属性错误。");
        }
        mav.addObject("naturalSize", imageSize);
        mav.addObject("scaledSize", new ImageSize(270, 270));
        mav.addObject("pictureUrl", appConst.getUploadPublicUrlPath() + "/tmp/"  + filename);
        return mav;
    }

    @RequestMapping("/admin/setting/user-avatar-crop")
    public String defaultAvatarCropAction(HttpServletRequest request) {
        Map<String, Object> options = ParamMap.toFormDataMap(request);

        Map<String, Object> setting = settingService.get(DEFAULT_SETTING_KEY_NAME);
        setting.put("defaultAvatar", 1);
        settingService.set(DEFAULT_SETTING_KEY_NAME, setting);
        String filename = String.valueOf(setting.get("defaultAvatarFileName"));
        String directory = appConst.getUploadPublicDirectory() + "/tmp";
        String path = appConst.getStoragePath() + "/web/files/assets/img/default/";
        FastDirectoryUtil.dirNotExistsThenMake(path);

        String pictureFilePath = directory + "/" + filename;
        AppPathInfo pathInfo = new AppPathInfo(pictureFilePath);

        String cropImageFilePath = pathInfo.getDirname() + "/" + pathInfo.getFilename() + "_crop." + pathInfo.getExtension();
        int x = (int)ValueParser.parseFloat(options.get("x"));
        int y = (int)ValueParser.parseFloat(options.get("y"));
        int width = (int)ValueParser.parseFloat(options.get("width"));
        int height = (int)ValueParser.parseFloat(options.get("height"));
        ImageUtil.cropPartImage(pictureFilePath, cropImageFilePath, pathInfo.getExtension(), x, y, width, height);

        String largeFilePath = pathInfo.getDirname() + "/" + pathInfo.getFilename() + "_large." + pathInfo.getExtension();
        try {
            ImageUtil.resizeImage(cropImageFilePath, largeFilePath, pathInfo.getExtension(), 220, 0.9f);
            FileCopyUtils.copy(new File(largeFilePath), new File(path + "large" + filename));
        } catch (Exception e) {
            throw new RuntimeGoingException("Resize large user image error: " + e.getMessage());
        }

        String smallFilePath = pathInfo.getDirname() + "/" + pathInfo.getFilename() + "_small." + pathInfo.getExtension();
        try {
            ImageUtil.resizeImage(cropImageFilePath, smallFilePath, pathInfo.getExtension(), 120, 0.9f);
            FileCopyUtils.copy(new File(smallFilePath), new File(path + filename));
        } catch (Exception e) {
            throw new RuntimeGoingException("Resize large user image error: " + e.getMessage());
        }

        return "redirect:/admin/setting/user-avatar";
    }

}
