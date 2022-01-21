package com.jetwinner.toolbag;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xulixin
 */
public final class FileToolkit {

    private FileToolkit() {
        // reserved.
    }

    private static final String[] IMAGE_FILE_EXT_NAME = {"bmp", "gif", "jpg", "jpeg", "png"};

    public static String hash(String filenamePrefix) {
        if (filenamePrefix == null || filenamePrefix.length() == 0) {
            throw new IllegalArgumentException("Filename Prefix cannot be null or zero length");
        }
        String str = filenamePrefix + System.currentTimeMillis();
        str = str.substring(str.length() - 9);

        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();

            for (byte element : hash) {
                if ((0xff & element) < 0x10) {
                    hexString.append('0').append(Integer.toHexString((0xFF & element)));
                } else {
                    hexString.append(Integer.toHexString(0xFF & element));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            // noops;
        }

        return hexString.toString();
    }

    public static String getFileExtension(String name) {
        return name.substring(name.indexOf(".") + 1);
    }

    public static boolean isImageFile(String filename) {
        for (int i = 0; i < IMAGE_FILE_EXT_NAME.length; i++) {
            if (filename.endsWith(IMAGE_FILE_EXT_NAME[i])) {
                return true;
            }
        }
        return false;
    }
}
