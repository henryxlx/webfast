package com.jetwinner.webfast.email;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.kernel.service.AppSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author xulixin
 */
@Service
public class FastEmailServiceImpl implements FastEmailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AppSettingService settingService;
    private final MailSenderConfig mailSenderConfig;

    public FastEmailServiceImpl(AppSettingService settingService, MailSenderConfig mailSenderConfig) {
        this.settingService = settingService;
        this.mailSenderConfig = mailSenderConfig;
    }

    @Override
    public void sendEmail(String to, String title, String body, MailTypeEnum mailType) throws Exception {
        Map<String, Object> config = settingService.get("mailer");

        if (EasyStringUtil.isBlank(config.get("enabled"))) {
            return;
        }

        String from = String.valueOf(config.get("from"));
        JavaMailSender mailSender = mailSenderConfig.buildMailSender();
        try {
            if (mailType == MailTypeEnum.HtmlMail) {
                sendHtmlMail(mailSender, from, to, title, body);
            } else {
                sendSimpleMail(mailSender, from, to, title, body);
            }
            logger.info("Mail has been sent successfully.");
        } catch (MessagingException e) {
            logger.error("Mail sending error! ", e);
            throw e;
        }
    }

    @Override
    public void sendEmail(String to, String title, String body) throws Exception {
        sendEmail(to, title, body, MailTypeEnum.TextMail);
    }

    /**
     * ??????????????????
     *
     * @param to      ?????????
     * @param subject ??????
     * @param content ??????
     */
    public void sendSimpleMail(JavaMailSender mailSender, String from, String to, String subject, String content) {
        //??????SimpleMailMessage??????
        SimpleMailMessage message = new SimpleMailMessage();
        //???????????????
        message.setFrom(from);
        //???????????????
        message.setTo(to);
        //????????????
        message.setSubject(subject);
        //????????????
        message.setText(content);
        //????????????
        mailSender.send(message);
    }

    /**
     * html??????
     *
     * @param to      ?????????,????????????????????? ???"xxx@xxx.com,xxx@xxx.com,xxx@xxx.com"
     * @param subject ??????
     * @param content ??????
     */
    public void sendHtmlMail(JavaMailSender mailSender, String from, String to,
                             String subject, String content) throws MessagingException {

        //??????MimeMessage??????
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //???????????????
            messageHelper.setFrom(from);
            //???????????????,???????????????????????????
            InternetAddress[] internetAddressTo = InternetAddress.parse(to);
            messageHelper.setTo(internetAddressTo);
            //messageHelper.setTo(to);
            //????????????
            message.setSubject(subject);
            //???????????????html??????
            messageHelper.setText(content, true);
            //??????
            mailSender.send(message);
    }

    /**
     * ??????????????????
     *
     * @param to       ?????????
     * @param subject  ??????
     * @param content  ??????
     * @param filePath ??????
     */
    public void sendAttachmentsMail(JavaMailSender mailSender, String from, String to,
                                    String subject, String content, String filePath) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }
}
