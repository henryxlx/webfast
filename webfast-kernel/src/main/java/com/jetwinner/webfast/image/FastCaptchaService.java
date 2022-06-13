package com.jetwinner.webfast.image;

import java.awt.image.BufferedImage;

/**
 * @author xulixin
 */
public interface FastCaptchaService {

    String createText();

    BufferedImage createImage(String text);
}
