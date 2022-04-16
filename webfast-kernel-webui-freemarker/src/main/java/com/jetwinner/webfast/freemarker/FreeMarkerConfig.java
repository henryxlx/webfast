package com.jetwinner.webfast.freemarker;

import com.jetwinner.webfast.freemarker.ext.JsonEncodeFunction;
import com.jetwinner.webfast.freemarker.tag.RenderControllerTag;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xulixin
 */
@Configuration
public class FreeMarkerConfig {

    protected final freemarker.template.Configuration configuration;

    private final RenderControllerTag renderControllerTag;

    public FreeMarkerConfig(freemarker.template.Configuration configuration,
                            RenderControllerTag renderControllerTag) {

        this.configuration = configuration;
        this.renderControllerTag = renderControllerTag;
    }

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        configuration.setSharedVariable(RenderControllerTag.MODEL_VAR_NAME, renderControllerTag);
        ArrayList<String> list = new ArrayList<>();
        list.add("/html_extension.ftl");
        configuration.setAutoIncludes(list);
        HashMap<String, String> importMap = new HashMap<>(2);
        importMap.put("web_macro", "/web_macro.ftl");
        importMap.put("admin_macro", "/admin/admin_macro.ftl");
        configuration.setAutoImports(importMap);
        configuration.setSharedVariable("json_encode", new JsonEncodeFunction());
    }
}
