package com.jetwinner.webfast.mvc.controller;

import com.jetwinner.webfast.image.FastCaptchaService;
import com.jetwinner.webfast.mvc.extension.FastCaptchaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 * @author xulixin
 */
@Controller
public class CaptchaNumController {

    private static final String CAPTCHA_PROVIDER_NAME = "kaptcha";

    private static final Logger log = LoggerFactory.getLogger(CaptchaNumController.class);

    private final Optional<FastCaptchaService> fastCaptchaServiceOptional;

    public CaptchaNumController(ApplicationContext applicationContext) {
        this.fastCaptchaServiceOptional = Optional.ofNullable(applicationContext.getBean(FastCaptchaService.class));
    }

    @RequestMapping("/captcha_num")
    public void captchaAction(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Content-Disposition", "inline; filename=reg_captcha.png");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
        try {
            String capText = "blank";
            if (fastCaptchaServiceOptional.isPresent()) {
                capText = fastCaptchaServiceOptional.get().createText();
                BufferedImage image = fastCaptchaServiceOptional.get().createImage(capText);
                ImageIO.write(image,"png", response.getOutputStream());
            } else {
                capText = FastCaptchaUtil.generateCaptcha(150, 32, response.getOutputStream());
            }
            session.setAttribute("CAPTCHA_SESSION_KEY", capText);
        } catch (Exception e) {
            log.error("Generate captcha image error: " + e.getMessage());
        }
    }
}
