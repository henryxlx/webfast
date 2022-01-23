package com.jetwinner.webfast.mvc.controller.admin;

import com.jetwinner.security.UserAccessControlService;
import com.jetwinner.servlet.RequestIpAddressUtil;
import com.jetwinner.toolbag.FileToolkit;
import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.image.ImageSize;
import com.jetwinner.webfast.image.ImageUtil;
import com.jetwinner.webfast.kernel.AppUser;
import com.jetwinner.webfast.kernel.FastAppConst;
import com.jetwinner.webfast.kernel.Paginator;
import com.jetwinner.webfast.kernel.dao.support.OrderBy;
import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.kernel.model.AppModelRole;
import com.jetwinner.webfast.kernel.service.AppRoleService;
import com.jetwinner.webfast.kernel.service.AppUserService;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import com.jetwinner.webfast.mvc.BaseControllerHelper;
import com.jetwinner.webfast.session.FlashMessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xulixin
 */
@Controller("webfastAdminUserController")
public class UserController {

    private final AppUserService userService;
    private final AppRoleService roleService;
    private final UserAccessControlService userAccessControlService;
    private final FastAppConst appConst;

    public UserController(AppUserService userService,
                          AppRoleService roleService,
                          UserAccessControlService userAccessControlService,
                          FastAppConst appConst) {

        this.userService = userService;
        this.roleService = roleService;
        this.userAccessControlService = userAccessControlService;
        this.appConst = appConst;
    }

    @RequestMapping("/admin/user")
    public String indexPage(HttpServletRequest request, Model model) {
        Map<String, Object> conditions = ParamMap.toConditionMap(request);
        Paginator paginator = new Paginator(request, userService.searchUserCount(conditions), 20);

        model.addAttribute("roles", roleService.listAllRole());
        model.addAttribute("users", userService.searchUsers(conditions,
                OrderBy.builder().addDesc("createdTime"),
                paginator.getOffsetCount(),
                paginator.getPerPageCount()));
        model.addAttribute(Paginator.MODEL_ATTR_NAME, paginator);
        return "/admin/user/index";
    }

    @GetMapping("/admin/user/create")
    public String createUserPage() {
        return "/admin/user/create-modal";
    }

    @PostMapping("/admin/user/create")
    public String createUserAction(HttpServletRequest request) {
        Map<String, Object> formData = ParamMap.toPostDataMap(request);
        Map<String, Object> userData = ParamMap.filterConditionMap(formData,
                "email", "username", "password");
        userData.put("createdIp", RequestIpAddressUtil.getClientIp(request));
        AppUser user = userService.register(userData);
        request.getSession().setAttribute("register_email", user.getEmail());

        if (EasyStringUtil.isNotBlank(formData.get("roles"))) {
            userService.changeUserRoles(user.getId(), "ROLE_TEACHER", "ROLE_USER");
        }

        // logService.info("user", "add", String.format("管理员添加新用户 {%s} (%d)", user.getUsername(), user.getId()));

        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/create-email-check")
    @ResponseBody
    public Map<String, Object> checkEmailAction(String value) {
        String email = value.replace("!", ".");
        boolean result = userService.existEmail(email);
        Map<String, Object> map = new HashMap<>(2);
        if (result) {
            map.put("success", Boolean.FALSE);
            map.put("message", "该Email地址已经被使用，请重新修改！");
        } else {
            map.put("success", Boolean.TRUE);
            map.put("message", "该Email地址可以使用");
        }
        return map;
    }

    @RequestMapping("/admin/user/create-username-check")
    @ResponseBody
    public Map<String, Object> usernameCheckAction(String value) {
        AppUser user = userService.getUserByUsername(value);
        Map<String, Object> map = new HashMap<>(2);
        if (user != null) {
            map.put("success", Boolean.FALSE);
            map.put("message", "该用户名已存在，不能被使用");
        } else {
            map.put("success", Boolean.TRUE);
            map.put("message", "该用户名可以使用");
        }
        return map;
    }

    @GetMapping("/admin/user/{id}/edit")
    public String editPage(@PathVariable Integer id, Model model) {
        AppUser user = userService.getUser(id);
        Map<String, Object> profile = userService.getUserProfile(id);
        profile.put("title", user.getTitle());
        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAllRole());
        // model.addAttribute("fields", getFields());
        return "/admin/user/edit-modal";
    }

    @PostMapping("/admin/user/{id}/edit")
    public String editAction(@PathVariable Integer id, HttpServletRequest request, Model model) {
        AppUser user = userService.getUser(id);
        Map<String, Object> profile = userService.getUserProfile(id);
        profile.put("title", user.getTitle());

        Map<String, Object> profileMap = ParamMap.toPostDataMap(request);
        profileMap.put("title", user.getTitle());
        if (!(EasyStringUtil.isNotBlank(user.getVerifiedMobile()) &&
                EasyStringUtil.isNotBlank(profile.get("mobile")))) {

            userService.updateUserProfile(user.getId(), profileMap);
            // logService.info("user", "edit", String.format("管理员编辑用户资料 {%s} (#%d)", user.getUsername(), user.getId()), profile);
        } else {
            FlashMessageUtil.setFlashMessage("danger", "用户已绑定的手机不能修改。", request.getSession());
        }

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}/roles")
    public String rolePage(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        List<AppModelRole> list = roleService.listAllRole();
        Map<String, String> rolesMap = list.stream().collect(Collectors.toMap(AppModelRole::getRoleName, AppModelRole::getLabel));
        model.addAttribute("rolesMap", rolesMap);
        return "/admin/user/roles-modal";
    }

    @PostMapping("/admin/user/{id}/roles")
    public String userRolesAction(@PathVariable Integer id,
                                  @RequestParam("roles[]") String[] roles,
                                  Model model) {

        model.addAttribute("user", userService.getUser(id));
        userService.changeUserRoles(id, roles);
        return "/admin/user/user-table-tr";
    }

    @GetMapping("/admin/user/{id}/avatar")
    public String avatarPage(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "/admin/user/user-avatar-modal";
    }

    @PostMapping("/admin/user/{id}/avatar")
    public ModelAndView avatarAction(@PathVariable Integer id,
                                     @RequestParam(value = "form[avatar]", required = false) MultipartFile file) {

        if (file == null) {
            return BaseControllerHelper.createMessageResponse("error", "没有可供上传的文件，请选择文件。");
        }
        if (!FileToolkit.isImageFile(file.getOriginalFilename())) {
            return BaseControllerHelper.createMessageResponse("error", "上传图片格式错误，请上传jpg, gif, png格式的文件。");
        }

        String filenamePrefix = String.format("user_%d_", id);
        String hash = FileToolkit.hashFilename(filenamePrefix);
        String ext = FileToolkit.getFileExtension(file.getOriginalFilename());
        String filename = filenamePrefix + hash + "!"  + ext;

        String directory = appConst.getUploadPublicDirectory() + "/tmp/";
        try {
            file.transferTo(new File(directory + filename));
        } catch (IOException e) {
            return BaseControllerHelper.createMessageResponse("error", "图片上传失败，请检查上传目录或文件是否存在。");
        }

        ModelAndView mav = new ModelAndView("/admin/user/user-avatar-crop-modal");
        try {
            this.avatar2(id, filename, mav);
        } catch (Exception e) {
            return BaseControllerHelper.createMessageResponse("error", "该文件为非图片格式文件，请重新上传。");
        }

        mav.addObject("user", userService.getUser(id));
        mav.addObject("filename", filename);
        return mav;
    }

    private void avatar2(Integer id, String filename, ModelAndView mav) throws Exception {
        if (!userAccessControlService.hasRole("ROLE_SUPER_ADMIN")) {
            throw new RuntimeGoingException("Need ROLE_SUPER_ADMIN role to change user avatar.");
        }

        String pictureFilePath = appConst.getUploadPublicDirectory() + "/tmp/"  + filename;

        ImageSize imageSize = ImageUtil.getNaturalSize(new File(pictureFilePath));
        mav.addObject("naturalSize", imageSize);
        mav.addObject("scaledSize", new ImageSize(270, 270));
        mav.addObject("pictureUrl", "tmp/"  + filename);
    }

    @PostMapping("/admin/user/{id}/avatar/crop")
    @ResponseBody
    public Boolean avatarCropAction(@PathVariable Integer id, HttpServletRequest request) {
        if (!userAccessControlService.hasRole("ROLE_SUPER_ADMIN")) {
            throw new RuntimeGoingException("Need ROLE_SUPER_ADMIN role to crop user avatar.");
        }

        Map<String, Object> options = ParamMap.toPostDataMap(request);
        String filename = request.getParameter("filename");
        String pictureFilePath = appConst.getUploadPublicDirectory() + "/tmp/" + filename;
        userService.changeAvatar(id, pictureFilePath, options);

        // return "redirect:/admin/user";
        return Boolean.TRUE;
    }

    @GetMapping("/admin/user/{id}/change-password")
    public String changePasswordPage(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "/admin/user/change-password-modal";
    }

    @PostMapping("/admin/user/{id}/change-password")
    @ResponseBody
    public Boolean changePasswordAction(@PathVariable Integer id, String newPassword) {
        userService.changePassword(id, null, newPassword);
        return Boolean.TRUE;
    }

    @GetMapping("/admin/user/{id}")
    public String showUserPage(@PathVariable Integer id, Model model) {
        AppUser user = userService.getUser(id);
        Map<String, Object> profile = userService.getUserProfile(id);
        profile.put("title", user.getTitle());
        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAllRole());
        // model.addAttribute("fields", getFields());
        return "/admin/user/show-modal";
    }

    @PostMapping("/admin/user/{id}/lock")
    public String lockAction(@PathVariable Integer id, Model model) {
        userService.lockUser(id);
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.listAllRole());
        return "/admin/user/user-table-tr";
    }

    @PostMapping("/admin/user/{id}/unlock")
    public String unlockAction(@PathVariable Integer id, Model model) {
        userService.unlockUser(id);
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.listAllRole());
        return "/admin/user/user-table-tr";
    }

    @GetMapping("/default/message")
    public ModelAndView message() {
        return BaseControllerHelper.createMessageResponse("error", "上传图片格式错误，请上传jpg, gif, png格式的文件。");
    }
}
