package com.jetwinner.webfast.freemarker;

/**
 * @author xulixin
 */
public class FastLibraryObject {

    public static final String MODEL_VAR_NAME = "fastLib";

    Integer percent(Integer part, Integer whole) {
        if (part != null && whole != null) {
            return (int) (part.intValue() * 1.0 / whole.intValue() * 1.0 * 100);
        }
        return 0;
    }
}
