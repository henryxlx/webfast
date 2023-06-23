package com.jetwinner.toolbag;

import com.jetwinner.util.ArrayUtil;
import com.jetwinner.util.FastEncryptionUtil;
import com.jetwinner.util.FastTimeUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author xulixin
 */
public final class FileToolkit {

    private FileToolkit() {
        // reserved.
    }

    private static final String[] IMAGE_FILE_EXT_NAME_ARRAY = {"bmp", "gif", "jpg", "jpeg", "png"};

    private static final String SECURITY_FILE_EXT_NAME = "jpg jpeg gif png txt doc docx xls xlsx pdf ppt pptx pps ods odp mp4 mp3 avi flv wmv wma mov zip rar gz tar 7z swf ico";

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

    public static void transferFile(MultipartFile file, String directory, String filename) throws IOException {
        File fileDir = new File(directory);
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                throw new IOException("Create directory: " + directory + " error! Can not transfer file");
            }
        }
        file.transferTo(new File(directory + "/" + filename));
    }

    public static String getFileTypeByExtension(String extension) {
        extension = extension != null ? extension.toLowerCase() : "";

        if (ArrayUtil.inArray(extension, "mp4", "avi", "wmv", "flv", "mov")) {
            return "video";
        } else if (ArrayUtil.inArray(extension, "mp3", "wma")) {
            return "audio";
        } else if (ArrayUtil.inArray(extension, "jpg", "jpeg", "gif", "png")) {
            return "image";
        } else if (ArrayUtil.inArray(extension, "txt", "doc", "docx", "xls", "xlsx", "pdf")) {
            return "document";
        } else if (ArrayUtil.inArray(extension, "ppt", "pptx")) {
            return "ppt";
        } else {
            return "other";
        }
    }

    public static boolean validateFileExtension(String extension) {
        return SECURITY_FILE_EXT_NAME.indexOf(extension) == -1;
    }

    public static String generateFilename(String ext) {
        String filename = FastTimeUtil.timeToDateTimeStr("yyyyMMddHHmmss", System.currentTimeMillis())
                + "-" + FastEncryptionUtil.sha1(String.valueOf(new Random().nextInt())).substring(0, 6);
        return filename + "." + ext;
    }

    public static boolean delete(String fullPath) {
        return deleteFile(toFile(fullPath));
    }

    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                boolean isOk = cleanFile(file);
                if (!isOk) {
                    return false;
                }
            }

            return file.delete();
        } else {
            return true;
        }
    }

    public static boolean cleanFile(File directory) {
        if (directory == null || directory.exists() == false || false == directory.isDirectory()) {
            return true;
        }

        final File[] files = directory.listFiles();
        if (null != files) {
            boolean isOk;
            for (File childFile : files) {
                isOk = deleteFile(childFile);
                if (isOk == false) {
                    // 删除一个出错则本次删除任务失败
                    return false;
                }
            }
        }
        return true;
    }

    public static File toFile(String fullPath) {
        return null == fullPath ? null : new File(fullPath);
    }
}
