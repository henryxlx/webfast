package com.jetwinner.webfast.kernel.service.content.type;

/**
 * @author xulixin
 */
public abstract class BaseContentType {

    public abstract String getAlias();

    public abstract String getName();

    public String[] getBasicFields() {
        return new String[]{"title", "content"};
    }
}
