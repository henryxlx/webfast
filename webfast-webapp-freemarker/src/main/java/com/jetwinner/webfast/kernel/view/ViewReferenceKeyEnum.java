package com.jetwinner.webfast.kernel.view;

/**
 * @author xulixin
 */
public enum ViewReferenceKeyEnum {

    AppConst("appConst"),
    DataHolder("dictHolder"),
    RequestContextPath("ctx"),
    UserAcl("userAcl");

    private String name;

    ViewReferenceKeyEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
