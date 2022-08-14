package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.servlet.RequestContextPathUtil;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.util.FastEncryptionUtil;
import com.jetwinner.util.PhpStringUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelMessageConversation;
import com.jetwinner.webfast.kernel.service.AppMessageService;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import com.jetwinner.webfast.kernel.service.AppUserFieldService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
@Controller("webfastSiteRegisterController")
public class RegisterController {

    private final UserAccessControlService userAccessControlService;
    private final AppSettingService settingService;
    private final AppUserFieldService userFieldService;
    private final AppUserService userService;
    private final AppMessageService messageService;
    private final UserAccessControlService aclService;

    @Value("${custom.app.hashkey:x19m06d03yt71cn06d17cl15}")
    private String userHashSecretKey;

    public RegisterController(UserAccessControlService userAccessControlService,
                              AppSettingService settingService,
                              AppUserFieldService userFieldService,
                              AppUserService userService,
                              AppMessageService messageService,
                              UserAccessControlService aclService) {

        this.userAccessControlService = userAccessControlService;
        this.settingService = settingService;
        this.userFieldService = userFieldService;
        this.userService = userService;
        this.messageService = messageService;
        this.aclService = aclService;
    }

    @GetMapping("/register")
    public ModelAndView indexPage(HttpServletRequest request) {
        AppUser user = AppUser.getCurrentUser(request);
        if (userAccessControlService.isLoggedIn()) {
            return BaseControllerHelper.createMessageResponse("info", "你已经登录了", null, 3000, "/index");
        }

        Map<String, Object> auth = settingService.get("auth");

        boolean registerEnable = true;
        if (auth.containsKey("register_mode")) {
            registerEnable = "opened".equals(auth.get("register_mode"));
        }
        if (!registerEnable) {
            return BaseControllerHelper.createMessageResponse("info", "注册已关闭，请联系管理员", null, 3000, "/index");
        }

        if (!auth.containsKey("registerSort")) {
            auth.put("registerSort", "");
        }

        List<Map<String, Object>> userFields = userFieldService.getAllFieldsOrderBySeqAndEnabled();
        String[][] fieldTypeMappings = new String[][]{{"textField", "text"}, {"varcharField", "varchar"},
                {"intField", "int"}, {"floatField", "float"}, {"dateField", "date"}};
        for (Map<String, Object> userField : userFields) {
            for (String[] fieldTypePair : fieldTypeMappings) {
                if (fieldTypePair[0].equals(userField.get("fieldName"))) {
                    userField.put("type", fieldTypePair[1]);
                    break;
                }
            }
        }

        if ("1".equals(settingService.getSettingValue("cloud_sms.sms_enabled", "0"))
                && "on".equals(settingService.getSettingValue("cloud_sms.sms_registration", "off"))
                && !EasyStringUtil.contains(String.valueOf(auth.get("registerSort")), "mobile")) {
            auth.put("registerSort", "mobile");
        }

        ModelAndView mav = new ModelAndView("/register/index");
        mav.addObject("isRegisterEnabled", registerEnable);
        mav.addObject("registerSort", auth.get("registerSort"));
        mav.addObject("userFields", userFields);
        mav.addObject("_target_path", getTargetPath(request));
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerAction(HttpServletRequest request) {
        Map<String, Object> registration = ParamMap.toFormDataMap(request);
        Map<String, Object> authSettings = settingService.get("auth");
        if (authSettings.containsKey("captcha_enabled") && ("1".equals(authSettings.get("captcha_enabled")))) {
            String captchaCodePostedByUser = String.valueOf(registration.get("captcha_num")).toLowerCase();
            String captchaCode = String.valueOf(request.getSession().getAttribute("captcha_code"));
            if (captchaCodePostedByUser == null || captchaCodePostedByUser.length() < 5) {
                throw new RuntimeGoingException("验证码错误。");
            }
            if (captchaCode == null || captchaCode.length() < 5) {
                throw new RuntimeGoingException("验证码错误。");
            }
            if (captchaCode != captchaCodePostedByUser) {
                request.getSession().setAttribute("captcha_code", PhpStringUtil.mt_rand(0, 999999999));
                throw new RuntimeGoingException("验证码错误。");
            }
            request.getSession().setAttribute("captcha_code", PhpStringUtil.mt_rand(0, 999999999));
        }

        registration.put("verifiedMobile", "");
        if (authSettings.containsKey("registerSort")
                && EasyStringUtil.contains(String.valueOf(authSettings.get("registerSort")), "mobile")
                && "1".equals(this.getCloudSmsKey("sms_enabled")) &&
                "on".equals(this.getCloudSmsKey("sms_registration"))) {

            // list($result, $sessionField, $requestField) = SmsToolkit::smsCheck($request, $scenario = 'sms_registration');
            boolean result = false;
            if (result) {
                // registration.put("verifiedMobile", sessionField['to']);
            } else {
                return BaseControllerHelper.createMessageResponse("info", "手机短信验证错误，请重新注册");
            }
        }

        registration.put("createdIp", request.getRemoteAddr());
        if (authSettings.containsKey("register_protective")) {
            boolean status = this.protectiveRule(authSettings.get("register_protective"), registration.get("createdIp"));
            if (!status) {
                return BaseControllerHelper.createMessageResponse("info", "由于您注册次数过多，请稍候尝试");
            }
        }

        AppUser user = userService.register(registration);

        if (authSettings.containsKey("email_enabled") && ("closed".equals(authSettings.get("email_enabled")))) {
            this.authenticateUser(request, user);
            this.sendRegisterMessage(user);
        }

        String gotoUrl = BaseControllerHelper.generateUrl("/register/submited",
                new ParamMap().add("id", user.getId()).add("hash", this.makeHash(user))
                        .add("goto", this.getTargetPath(request)).toMap());

        if (this.hasPartnerAuth()) {
            this.authenticateUser(request, user);
            this.sendRegisterMessage(user);
            return BaseControllerHelper.redirect(BaseControllerHelper.generateUrl("/partner/login",
                    new ParamMap().add("goto", gotoUrl).toMap()));
        }

        Map<String, Object> mailerSetting = settingService.get("mailer");
        if ("true".equals(mailerSetting.get("enabled"))) {
            return BaseControllerHelper.redirect(this.getTargetPath(request));
        }
        return BaseControllerHelper.redirect(gotoUrl);
    }

    private boolean protectiveRule(Object type, Object ip) {
        if ("middle".equals(type)) {
            Map<String, Object> condition = new ParamMap()
                    .add("startTime", System.currentTimeMillis() - 24 * 3600).add("createdIp", ip).toMap();
            int registerCount = userService.searchUserCount(condition);
            return registerCount > 30 ? false : true;
        }
        if ("high".equals(type)) {
            Map<String, Object> condition = new ParamMap()
                    .add("startTime", System.currentTimeMillis() - 24 * 3600).add("createdIp", ip).toMap();
            int registerCount = userService.searchUserCount(condition);
            if (registerCount > 10) {
                return false;
            }
            registerCount = userService.searchUserCount(new ParamMap()
                    .add("", System.currentTimeMillis() - 3600).add("createdIp", ip).toMap());
            return registerCount >= 1 ? false : true;
        }
        return true;
    }

    private String makeHash(AppUser user) {
        String str = user.getId() + user.getLocked() + userHashSecretKey;
        return FastEncryptionUtil.md5(str);
    }

    private void sendRegisterMessage(AppUser user) {
        Map<String, Object> auth = settingService.get("auth");

        if (!auth.containsKey("welcome_enabled")) {
            return ;
        }

        if ("opened".equals(auth.get("welcome_enabled"))) {
            return ;
        }

        if (EasyStringUtil.isBlank(auth.containsKey("welcome_sender"))) {
            return ;
        }

        AppUser senderUser = userService.getUserByUsername(String.valueOf(auth.get("welcome_sender")));
        if (senderUser == null) {
            return ;
        }

        String welcomeBody = this.getWelcomeBody(user);
        if (EasyStringUtil.isBlank(welcomeBody)) {
            return;
        }

        if (welcomeBody.length() >= 1000) {
            welcomeBody = EasyStringUtil.plainTextFilter(welcomeBody, 1000);
        }

        messageService.sendMessage(senderUser.getId(), user.getId(), welcomeBody);

        AppModelMessageConversation conversation =
                messageService.getConversationByFromIdAndToId(user.getId(), senderUser.getId());

        messageService.deleteConversation(conversation.getId());
    }

    private String getWelcomeBody(AppUser user) {
        Map<String, Object> auth = settingService.get("auth");
        Map<String, Object> site = settingService.get("site");
        String[] placeHolderArray = {"{{nickname}}", "{{sitename}}", "{{siteurl}}"};
        String[] valuesToReplace = {user.getUsername(), String.valueOf(site.get("name")), String.valueOf(site.get("url"))};
        String welcomeBody = settingService.getSettingValue("auth.welcome_body", "注册欢迎的内容");
        for (int i = 0; i < valuesToReplace.length; i++) {
            welcomeBody = welcomeBody.replace(placeHolderArray[i], valuesToReplace[i]);
        }
        return welcomeBody;
    }

    private void authenticateUser(HttpServletRequest request, AppUser user) {
        user.setCreatedIp(request.getRemoteAddr());
        try {
            aclService.doLoginCheck(user.getUsername(), user.getPassword(), false);
        } catch (Exception e) {
            return;
        }

        Map<String, Object> loginBind = settingService.get("login_bind");
        if (EasyStringUtil.isBlank(loginBind.get("login_limit"))) {
            return ;
        }

        String sessionId = request.getSession().getId();
        userService.rememberLoginSessionId(user.getId(), sessionId);
    }

    private boolean hasPartnerAuth() {
        return false;
    }

    private String getCloudSmsKey(String keyName) {
        return "notSupport";
    }

    private String getTargetPath(HttpServletRequest request) {
        String targetPath;
        if (EasyStringUtil.isNotBlank(request.getParameter("goto"))) {
            targetPath = request.getParameter("goto");
        } else if (EasyStringUtil.isNotBlank(request.getSession().getAttribute("_target_path"))) {
            targetPath = String.valueOf(request.getSession().getAttribute("_target_path"));
        } else {
            targetPath = request.getHeader("Referer");
        }

        String appHomeUrl = RequestContextPathUtil.createBaseUrl(request) + "index";
        if (targetPath.contains("login")) {
            return appHomeUrl;
        }

        String[] url = targetPath.split("\\?");

        if (url[0].contains("/partner/logout")) {
            return appHomeUrl;
        }

        if (url[0].contains("/password/reset/update")) {
            targetPath = appHomeUrl;
        }

        return targetPath;
    }

    @RequestMapping("/register/email/check")
    @ResponseBody
    public Map<String, Object> emailCheckAction(String value) {
        String email = value.replaceAll("!", ".");
        Map<String, Object> map = new HashMap<>(2);
        boolean existEmail = userService.existEmail(email);
        if (existEmail) {
            map.put("success", false);
            map.put("message", "Email已存在!");
        } else {
            map.put("success", true);
            map.put("message", "");
        }
        return map;
    }

    @RequestMapping("/register/submited")
    public ModelAndView submitedAction(HttpServletRequest request, Integer id, String hash) {
        AppUser user = this.checkHash(id, hash);
        if (user == null) {
            throw new RuntimeGoingException("创建注册用户失败！请联系管理员");
        }

        Map<String, Object> auth = settingService.get("auth");
        ModelAndView mav = new ModelAndView();
        mav.addObject("hash", hash);
        mav.addObject("user", user);
        mav.addObject("emailLoginUrl", this.getEmailLoginUrl(user.getEmail()));
        mav.addObject("_target_path", this.getTargetPath(request));
        if (auth.containsKey("email_enabled") && "opened".equals(auth.get("email_enabled")) && !hasPartnerAuth()) {
            mav.setViewName("/register/email-verify");
        } else {
            mav.setViewName("/register/submited");
        }
        return mav;
    }

    private AppUser checkHash(Integer userId, String hash) {
        AppUser user = userService.getUser(userId);
        if (user == null) {
            return null;
        }

        if (!hash.equals(this.makeHash(user))) {
            return null;
        }

        return user;
    }

    public String getEmailLoginUrl(String email) {
        String host = email.substring(email.indexOf('@') + 1);
        if ("hotmail.com".equals(host)) {
            return "http://www." + host;
        }

        if ("gmail.com".equals(host)) {
            return "http://mail.google.com";
        }

        return "http://mail." + host;
    }

    @RequestMapping("/register/username/check")
    @ResponseBody
    public Map<String, Object> usernameCheckAction(String value) {
        boolean available = userService.isUsernameAvailable(value);
        Map<String, Object> map = new HashMap<>(2);
        if (available) {
            map.put("success", false);
            map.put("message", "名称已存在!");
        } else {
            map.put("success", true);
            map.put("message", "");
        }
        return map;
    }
}
