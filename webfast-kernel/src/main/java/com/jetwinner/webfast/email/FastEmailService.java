package com.jetwinner.webfast.email;

public interface FastEmailService {

    enum MailTypeEnum {TextMail, HtmlMail, HtmlMailAttachment};

    void sendEmail(String to, String title, String body, MailTypeEnum mailType) throws Exception;

    void sendEmail(String to, String title, String body) throws Exception;
}
