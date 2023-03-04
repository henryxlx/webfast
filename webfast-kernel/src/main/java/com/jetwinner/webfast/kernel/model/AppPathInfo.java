package com.jetwinner.webfast.kernel.model;

import com.jetwinner.util.PhpStringUtil;

/**
 * @author xulixin
 */
public class AppPathInfo {

    private static final String DEFAULT_FILE_EXT_SEPARATOR = ".";

    private String dirname;

    private String filename;

    private String extension;

    public AppPathInfo(String path) {
        this(path, DEFAULT_FILE_EXT_SEPARATOR);
    }

    public AppPathInfo(String path, String fileExtSeparator) {
        char pathSeparator = PhpStringUtil.getPathSeparatorChar(path);
        int pos = path.lastIndexOf(pathSeparator);
        this.dirname = pos > 0 ? path.substring(0, pos) : "";
        this.filename = path.substring(pos > 0 ? pos + 1 : 0);
        parseFileExtension(fileExtSeparator);
    }

    private void parseFileExtension(String fileExtSeparator) {
        int pos = this.filename.indexOf(fileExtSeparator);
        if (pos < 1 && !DEFAULT_FILE_EXT_SEPARATOR.equals(fileExtSeparator)) {
            pos = this.filename.indexOf(DEFAULT_FILE_EXT_SEPARATOR);
        }
        this.extension = pos > 0 ? this.filename.substring(pos + 1) : "";
        this.filename = pos > 0 ? this.filename.substring(0, pos) : this.filename;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
