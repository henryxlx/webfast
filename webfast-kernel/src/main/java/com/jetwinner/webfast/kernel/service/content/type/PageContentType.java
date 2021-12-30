package com.jetwinner.webfast.kernel.service.content.type;

import org.springframework.stereotype.Component;

/**
 * @author xulixin
 */
@Component
public class PageContentType extends BaseContentType {

    @Override
    public String[] getBasicFields() {
        return new String[]{"title", "body", "picture", "alias", "template", "editor"};
    }

    @Override
    public String getAlias() {
        return "page";
    }

    @Override
    public String getName() {
        return "页面";
    }
}
