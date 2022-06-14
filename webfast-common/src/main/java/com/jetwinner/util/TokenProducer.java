package com.jetwinner.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class TokenProducer {

    public static final String DEFAULT_TOKEN_KEY = "webfast" + TokenProducer.class;

    private TokenProducer() {
        // reserved.
    }

    private static TokenProducer instance = new TokenProducer();

    public static TokenProducer getInstance() {
        return instance;
    }

    public String generateToken() {
        /*String osName = System.getProperty("os.name");
        String user = System.getProperty("user.name");
        System.out.println("当前操作系统是：" + osName);
        System.out.println("当前用户是：" + user);*/

        // 获取数据指纹，指纹是唯一的
        String value = System.currentTimeMillis() + new Random().nextInt(999999999) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] b = md.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "tokenLost";
    }
}
