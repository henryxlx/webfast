package com.jetwinner.webfast.freemarker;

import com.jetwinner.util.ListUtil;
import com.jetwinner.webfast.freemarker.tag.RenderControllerTag;
import com.jetwinner.webfast.kernel.typedef.ParamMap;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

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
        List list = ListUtil.newArrayList("/html_extension.ftl");
        configuration.setAutoIncludes(list);
        ParamMap paramMap = new ParamMap().add("web_macro", "/web_macro.ftl");
        paramMap.add("admin_macro", "/admin/admin_macro.ftl");
        configuration.setAutoImports(paramMap.toMap());
    }
}
