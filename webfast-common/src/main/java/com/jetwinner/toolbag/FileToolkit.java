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

    private static final String[] IMAGE_FILE_EXT_NAME_ARRAY = {"bmp", "gif", "jpg", "jpeg", "png"};

    public static String hashFilename(String filenamePrefix) {
        if (filenamePrefix == null || filenamePrefix.length() == 0) {
            throw new IllegalArgumentException("Filename Prefix cannot be null or zero length");
        }
        String str = filenamePrefix + System.currentTimeMillis();
        StringBuilder buf = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();

            for (byte element : hash) {
                if ((0xff & element) < 0x10) {
                    buf.append('0').append(Integer.toHexString((0xFF & element)));
                } else {
                    buf.append(Integer.toHexString(0xFF & element));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            // noops;
        }

        String hashStr = buf.toString();
        return hashStr.substring(hashStr.length() - 8);
    }

    public static String getFileExtension(String name) {
        return name.substring(name.indexOf(".") + 1);
    }

    public static boolean isImageFile(String filename) {
        for (String extName : IMAGE_FILE_EXT_NAME_ARRAY) {
            if (filename.endsWith(extName)) {
                return true;
            }
        }
        return false;
    }
}
