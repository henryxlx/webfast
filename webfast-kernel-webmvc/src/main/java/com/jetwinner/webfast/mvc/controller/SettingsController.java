package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.security.UserAccessControlService;
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
    private final UserAccessControlService userAccessControlService;
    private final FastAppConst appConst;

    public SettingsController(AppUserService userService,
                              AppUserFieldService userFieldService,
                              UserAccessControlService userAccessControlService,
                              FastAppConst appConst) {

        this.userService = userService;
        this.userFieldService = userFieldService;
        this.userAccessControlService = userAccessControlService;
        this.appConst = appConst;
    }

    @RequestMapping("")
    public String profilePage(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        Map<String, Object> profile = userService.getUserProfile(user.getId());
        user = userService.getUser(user.getId());
        boolean userProfileIsEmpty = profile.isEmpty();
        profile.put("title", user.getTitle());

        if ("POST".equals(request.getMethod())) {
            if (userProfileIsEmpty) {
                Map<String, Object> userProfileMap = ParamMap.toCustomFormDataMap(request);
                userService.updateUserProfile(user.getId(), userProfileMap);
                FlashMessageUtil.setFlashMessage("success", "用户基础信息添加成功。", request.getSession());
            } else {
                Map<String, Object> mapForUpdate = ParamMap.toUpdateDataMap(request, profile);
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
        Map<String, Object> formData = ParamMap.toFormDataMap(request);
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
            return BaseControllerHelper.createMessageResponse("error",
                    String.format("图片上传失败，请检查上传目录(%s)或文件(%s)是否存在。", directory, filename));
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
        Map<String, Object> options = ParamMap.toFormDataMap(request);
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

    private String securityQuestionsActionReturn(boolean hasSecurityQuestions,
                                                 List<Map<String, Object>> userSecureQuestions, Model model) {

        if (hasSecurityQuestions) {
            model.addAttribute("question1", userSecureQuestions.get(0).get("securityQuestionCode"));
            model.addAttribute("question2", userSecureQuestions.get(1).get("securityQuestionCode"));
            model.addAttribute("question3", userSecureQuestions.get(2).get("securityQuestionCode"));
        }
        model.addAttribute("hasSecurityQuestions", hasSecurityQuestions);

        return "/settings/security-questions";
    }

    @RequestMapping("/security_questions")
    public String securityQuestionsAction(HttpServletRequest request, Model model) {
        AppUser user = AppUser.getCurrentUser(request);
        List<Map<String, Object>> userSecureQuestions = userService.getUserSecureQuestionsByUserId(user.getId());
        boolean hasSecurityQuestions = userSecureQuestions != null && userSecureQuestions.size() > 0;

        if ("POST" .equals(request.getMethod())) {
            if (!userAccessControlService.checkPassword(user.getPassword(), request.getParameter("userLoginPassword"))) {
                FlashMessageUtil.setFlashMessage("danger",
                        "您的登陆密码错误，不能设置安全问题。", request.getSession());

                return securityQuestionsActionReturn(hasSecurityQuestions, userSecureQuestions, model);
            }

            if (hasSecurityQuestions) {
                throw new RuntimeGoingException("您已经设置过安全问题，不可再次修改。");
            }

            if (EasyStringUtil.equals(request.getParameter("question-1"), request.getParameter("question-2"))
                    || EasyStringUtil.equals(request.getParameter("question-1"), request.getParameter("question-3"))
                    || EasyStringUtil.equals(request.getParameter("question-2"), request.getParameter("question-3"))) {

                throw new RuntimeGoingException("2个问题不能一样。");
            }
            Map<String, Object> fields = new ParamMap()
                    .add("securityQuestion1", request.getParameter("question-1"))
                    .add("securityAnswer1", request.getParameter("answer-1"))
                    .add("securityQuestion2", request.getParameter("question-2"))
                    .add("securityAnswer2", request.getParameter("answer-2"))
                    .add("securityQuestion3", request.getParameter("question-3"))
                    .add("securityAnswer3", request.getParameter("answer-3")).toMap();

            userService.addUserSecureQuestionsWithUnHashedAnswers(user, fields);
            FlashMessageUtil.setFlashMessage("success", "安全问题设置成功。", request.getSession());
            hasSecurityQuestions = true;
            userSecureQuestions = userService.getUserSecureQuestionsByUserId(user.getId());
        }

        return securityQuestionsActionReturn(hasSecurityQuestions, userSecureQuestions, model);
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
