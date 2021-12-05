/*
 * Copyright (c) 2009-2019 ChangChun Genghis Technology Co.Ltd. All rights reserved.
 * ChangChun Genghis copyrights this specification. No part of this specification may be reproduced
 * in any form or means, without the prior written consent of ChangChun Genghis.
 *
 */

package com.jetwinner.webform;

/**
 * 定义HTML元素下拉列表(Select)中的选项对象(Option)，text代表选项显示的文本，value代表对应的值，value最终会发送给服务端.
 *
 * @author Lixin Xu
 * @since 1.0
 * Create with Intellij IDEA on 2019-07-28 21:14
 */
public class SelectOption {

    private String text;

    private String value;

    public SelectOption() {
    }

    public SelectOption(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
