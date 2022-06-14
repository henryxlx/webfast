package com.jetwinner.webfast.email;

import com.jetwinner.util.ValueParser;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class MailSenderConfig {

    private final AppSettingService settingService;

    public MailSenderConfig(AppSettingService settingService) {
        this.settingService = settingService;
    }

    public JavaMailSender buildMailSender() {
        Map<String, Object> mailConfig = settingService.get("mailer");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        mailSender.setHost(String.valueOf(mailConfig.get("host")));
        mailSender.setPort(ValueParser.parseInt(mailConfig.get("port")));
        mailSender.setProtocol("smtp");
        mailSender.setUsername(String.valueOf(mailConfig.get("username")));
        mailSender.setPassword(String.valueOf(mailConfig.get("password")));
        return mailSender;
    }
}
