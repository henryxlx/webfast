package com.jetwinner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author xulixin
 */
public final class FastDirectoryUtil {

    private static final Logger logger = LoggerFactory.getLogger(FastDirectoryUtil.class);

    private FastDirectoryUtil() {
        // reserved.
    }

    public static boolean dirExists(String path) {
        String toPath = path;
        if (!toPath.endsWith(File.separator)) {
            toPath = toPath + File.separator;
        }
        File toDirFile = new File(toPath);
        return toDirFile.exists();
    }

    public static boolean dirNotExists(String path) {
        return !dirExists(path);
    }

    public static boolean makeDir(String path) {
        String toPath = path;
        if (!toPath.endsWith(File.separator)) {
            toPath = toPath + File.separator;
        }
        File toDirFile = new File(toPath);
        if (toDirFile.exists()) {
            logger.debug("目录 " + toPath + " 已存在!");
            return false;
        }
        // 创建目录
        if (toDirFile.mkdirs()) {
            logger.debug("目录 " + toPath + " 创建成功!");
            return true;
        } else {
            logger.debug("目录 " + toPath + " 创建失败!");
            return false;
        }
    }

    public static void dirNotExistsThenMake(String path) {
        if (!dirExists(path)) {
            makeDir(path);
        }
    }

    public static void emptyDir(String dirPath) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (String path : children) {
                    deleteDir(new File(dir, path));
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null && children.length > 0) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
