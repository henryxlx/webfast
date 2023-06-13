package com.jetwinner.webfast.freemarker;

import com.jetwinner.webfast.freemarker.ext.DictTextFunction;
import com.jetwinner.webfast.freemarker.ext.JsonEncodeFunction;
import com.jetwinner.webfast.freemarker.ext.PlainTextExtractFunction;
import com.jetwinner.webfast.freemarker.tag.RenderControllerTag;
import com.jetwinner.webfast.kernel.FastDataDictHolder;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author xulixin
 */
@Configuration
public class FreeMarkerConfig {

    private static final Logger log = LoggerFactory.getLogger(FreeMarkerConfig.class);

    protected final freemarker.template.Configuration configuration;

    private final FastDataDictHolder dictHolder;

    private final RenderControllerTag renderControllerTag;

    public FreeMarkerConfig(freemarker.template.Configuration configuration,
                            FastDataDictHolder dictHolder,
                            RenderControllerTag renderControllerTag) {

        this.configuration = configuration;
        this.dictHolder = dictHolder;
        this.renderControllerTag = renderControllerTag;
    }

    /**
     * 添加自定义标签
     */
    @PostConstruct
    public void setSharedVariable() {
        ArrayList<String> list = new ArrayList<>();
        list.add("/html_extension.ftl");
        configuration.setAutoIncludes(list);
        HashMap<String, String> importMap = new HashMap<>(2);
        importMap.put("web_macro", "/web_macro.ftl");
        importMap.put("admin_macro", "/admin/admin_macro.ftl");
        configuration.setAutoImports(importMap);
        try {
            configuration.setSharedVariable("dict", dictHolder.getDict());
            configuration.setSharedVariable(FastLibraryObject.MODEL_VAR_NAME, new FastLibraryObject());
        } catch (TemplateModelException e) {
            log.error("Data Dictionary all data put in FreeMarker template error: " + e.getMessage());
        }
        configuration.setSharedVariable(RenderControllerTag.MODEL_VAR_NAME, renderControllerTag);
        configuration.setSharedVariable("json_encode", new JsonEncodeFunction());
        configuration.setSharedVariable(DictTextFunction.MODEL_VAR_NAME, new DictTextFunction(dictHolder));
        configuration.setSharedVariable(PlainTextExtractFunction.MODEL_VAR_NAME, new PlainTextExtractFunction());
    }
}
