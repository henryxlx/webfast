package com.jetwinner.webfast.shiro;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroPasswordUtilTest {

    public static final void main(String[] argc) throws Exception {
        // shiro 自带的工具类生成salt
        final String password = "admin123";
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String encryptPassword4Base64 = shiroEncryption(password, salt, false);
        String encryptPassword = shiroEncryption(password, salt);
        // System.setOut(new PrintStream(new FileOutputStream("d:/temp/user-password-salt.txt")));
        System.out.println("username=admin");
        System.out.println("password(plain text)=" + password);
        System.out.println("password(encrypt to Hex)=" + encryptPassword);
        System.out.println("password(encrypt to Base64)=" + encryptPassword4Base64);
        System.out.println("salt=" + salt);
    }

    static String shiroEncryption(String password, String salt) {
        return shiroEncryption(password, salt, true);
    }

    static String shiroEncryption(String password, String salt, boolean isHexEncoded) {
        // 算法名称
        String algorithmName = FastPasswordHelper.DEFAULT_HASH_ALGORITHM_NAME;
        // 加密次数
        int times = FastPasswordHelper.DEFAULT_HASH_ITERATIONS;
        SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, times);
        String encodedPassword = isHexEncoded ? simpleHash.toString() : simpleHash.toBase64();
        // 返回加密后的密码
        return encodedPassword;
    }

}