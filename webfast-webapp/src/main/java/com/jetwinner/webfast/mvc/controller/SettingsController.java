package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.toolbag.FileToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.image.ImageSize;
import com.jetwinner.webfast.image.ImageUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.service.AppUserFieldService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastSiteSettingController")
@RequestMapping(SettingsController.VIEW_PATH)
public class SettingsController {

    static final String VIEW_PATH ="/settings";

    private final AppUserService userService;
    private final AppUserFieldService userFieldService;
    private final FastAppConst appConst;

    public SettingsController(AppUserService userService,
                              AppUserFieldService userFieldService,
                              FastAppConst appConst) {

        this.userService = userService;
        this.userFieldService = userFieldService;
        this.appConst = appConst;
    }

    @RequestMapping("")
    public String profilePage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        Map<String, Object> profile = userService.getUserProfile(user.getId());
        profile.put("title", user.getTitle());

        if ("POST".equals(request.getMethod())) {
            Map<String, Object> mapForUpdate = ParamMap.toUpdateDataMap(request.getParameterMap(), profile);
            if (mapForUpdate != null && mapForUpdate.size() > 0) {
                if (!(StringUtils.hasLength(user.getVerifiedMobile()) &&
                        EasyStringUtil.isNotBlank(profile.get("mobile")))) {

                    userService.updateUserProfile(user.getId(), mapForUpdate);
                    FlashMessageUtil.setFlashMessage("success", "基础信息保存成功。", request.getSession());
                } else {
                    FlashMessageUtil.setFlashMessage("danger", "不能修改已绑定的手机。", request.getSession());
                }
            } else {
                FlashMessageUtil.setFlashMessage("danger", "没有可更新的数据。", request.getSession());
            }
            return "redirect:/settings";
        }

        List<Map<String, Object>> fields = userFieldService.getAllFieldsOrderBySeqAndEnabled();
        String[][] fieldDataTypeMappings = {{"textField", "text"}, {"varcharField", "varchar"},
                {"intField", "int"}, {"floatField", "float"}, {"dateField", "date"}};
        fields.forEach(field -> {
            for (String[] dataTypeMapping : fieldDataTypeMappings) {
                if (dataTypeMapping[0].equals(field.get("fieldName"))) {
                    field.put("type", dataTypeMapping[1]);
                }
            }
        });
        model.addAttribute("fields", fields);

        if (profile.containsKey("idcard") && "0".equals(profile.get("idcard"))) {
            profile.put("idcard", "");
        }
        model.addAttribute("profile", profile);
        model.addAttribute("fromCourse", request.getParameter("fromCourse"));
        model.addAttribute("user", user);
        return VIEW_PATH + "/profile";
    }

    @GetMapping("/approval/submit")
    public String approvalSubmitPage() {
        return "settings/approval";
    }

    @PostMapping("/approval/submit")
    public ModelAndView approvalSubmitAction(HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        Map<String, Object> formData = ParamMap.toPostDataMap(request);
        MultipartHttpServletRequest multipartReq = (MultipartHttpServletRequest) request;
        MultipartFile faceImgFile = multipartReq.getFile("faceImg");
        MultipartFile backImgFile = multipartReq.getFile("backImg");
        String faceImg = faceImgFile.getOriginalFilename();
        String backImg = backImgFile.getOriginalFilename();
        if (!FileToolkit.isImageFile(backImg) || !FileToolkit.isImageFile(faceImg)) {
            return BaseControllerHelper.createMessageResponse("error", "上传图片格式错误，请上传jpg, bmp,gif, png格式的文件。");
        }

        AppUser toAppUser = userService.getUser(user.getId());
        if (toAppUser == null) {
            throw new RuntimeGoingException(String.format("用户#%d不存在！", user.getId()));
        }

        String directory = appConst.getUploadPrivateDirectory() + "/approval";
        long now = System.currentTimeMillis();
        String faceImgPath = "userFaceImg" + user.getId() + now + "." + FileToolkit.getFileExtension(faceImg);
        String backImgPath = "userbackImg" + user.getId() + now + "." + FileToolkit.getFileExtension(backImg);
        try {
            faceImgFile.transferTo(new File(directory + "/" + faceImgPath));
            backImgFile.transferTo(new File(directory + "/" + backImgPath));
        } catch (IOException e) {
            BaseControllerHelper.createMessageResponse("error", "上传图片位置错误：" + e.getMessage());
        }
        userService.applyUserApproval(user.getId(), formData, faceImgPath, backImgPath, directory);
        FlashMessageUtil.setFlashMessage("success", "实名认证提交成功！", request.getSession());
        return new ModelAndView("redirect:/settings");
    }

    @GetMapping("/avatar")
    public String avatarPage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
//        boolean hasPartnerAuth = authService.hasPartnerAuth();
//        model.addAttribute("partnerAvatar", hasPartnerAuth ? authService.getPartnerAvatar(user.getId(), "big") : null);
        model.addAttribute("user", userService.getUser(user.getId()));
        model.addAttribute("fromCourse", request.getParameter("fromCourse"));
        return VIEW_PATH + "/avatar";
    }

    @PostMapping("/avatar")
    public ModelAndView avatarAction(@RequestParam(value = "avatar", required = false) MultipartFile file,
                             HttpServletRequest request, Model model) {

        AppUser user = AppUser.getCurrentUser(request);
        if (file == null) {
            return BaseControllerHelper.createMessageResponse("error", "没有可供上传的文件，请选择文件。");
        }
        if (!FileToolkit.isImageFile(file.getOriginalFilename())) {
            return BaseControllerHelper.createMessageResponse("error", "上传图片格式错误，请上传jpg, gif, png格式的文件。");
        }

        String filenamePrefix = String.format("user_%d_", user.getId());
        String hash = FileToolkit.hashFilename(filenamePrefix);
        String ext = FileToolkit.getFileExtension(file.getOriginalFilename());
        String filename = filenamePrefix + hash + "!"  + ext;

        String directory = appConst.getUploadPublicDirectory() + "/tmp/";
        try {
            file.transferTo(new File(directory + filename));
        } catch (IOException e) {
            return BaseControllerHelper.createMessageResponse("error", "图片上传失败，请检查上传目录或文件是否存在。");
        }

        ModelAndView mav = new ModelAndView("redirect:/settings/avatar-crop");
        mav.addObject("filename", filename);
        return mav;
    }

    @GetMapping("/avatar-crop")
    public ModelAndView avatarCropPage(String filename) {
        String pictureFilePath = appConst.getUploadPublicDirectory() + "/tmp/"  + filename;
        ImageSize imageSize = null;
        try {
            imageSize = ImageUtil.getNaturalSize(new File(pictureFilePath));
        } catch (Exception e) {
            return BaseControllerHelper.createMessageResponse("error", "该文件为非图片格式文件，请重新上传。");
        }

        ModelAndView mav = new ModelAndView(VIEW_PATH + "/avatar-crop");
        mav.addObject("naturalSize", imageSize);
        mav.addObject("scaledSize", new ImageSize(270, 270));
        mav.addObject("pictureUrl", "tmp/"  + filename);
        return mav;
    }

    @PostMapping("/avatar-crop")
    public String avatarCropAction(HttpServletRequest request, String filename) {
        AppUser user = AppUser.getCurrentUser(request);
        String pictureFilePath = appConst.getUploadPublicDirectory() + "/tmp/"  + filename;
        Map<String, Object> options = ParamMap.toPostDataMap(request);
        userService.changeAvatar(user.getId(), pictureFilePath, options);
        return "redirect:/settings/avatar";
    }

    @RequestMapping("/security")
    public String securityPage() {
        return VIEW_PATH + "/security";
    }

    @GetMapping("/password")
    public String passwordAction(HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        if (EasyStringUtil.isBlank(user.getSetup())) {
            return "redirect:/settings/setup";
        }
        return "settings/password";
    }

    @RequestMapping("/email")
    public String emailPage() {
        return VIEW_PATH + "/email";
    }

    @RequestMapping("/setup")
    public String setupPage() {
        return VIEW_PATH + "/setup";
    }
}
