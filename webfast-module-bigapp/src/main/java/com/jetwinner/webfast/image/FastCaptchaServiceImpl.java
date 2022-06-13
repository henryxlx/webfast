package com.jetwinner.webfast.image;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * @author xulixin
 */
@Service
public class FastCaptchaServiceImpl implements FastCaptchaService {

    Producer captcherProducer;

    public FastCaptchaServiceImpl() {
        Properties properties = new Properties();
        //下面是相应的配置
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.height","32");
        properties.setProperty("kaptcha.textproducer.font.size","28");
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        properties.setProperty("kaptcha.textproducer.char.length","4");
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        this.captcherProducer = kaptcha;
    }

    @Override
    public String createText() {
        return captcherProducer.createText();
    }

    @Override
    public BufferedImage createImage(String text) {
        return captcherProducer.createImage(text);
    }
}
