package com.jetwinner.webfast.kernel.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class ViewRenderServiceImpl implements ViewRenderService{

    private static final Logger log = LoggerFactory.getLogger(ViewRenderServiceImpl.class);

    protected final freemarker.template.Configuration configuration;

    public ViewRenderServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String renderView(String viewLocation, Map<String, Object> model) {
        return renderView(viewLocation, model, null);
    }

    @Override
    public String renderView(String viewLocation, Map<String, Object> model, Map<String, Object> importMacroModel) {
        try {
            Template t = configuration.getTemplate(viewLocation);
            if (importMacroModel != null && importMacroModel.size() > 0) {
                importMacroModel.forEach((k, v) -> t.addAutoImport(k, String.valueOf(v)));
            }
            StringWriter writer = new StringWriter();
            t.process(model, writer);
            writer.close();
            return writer.toString();
        } catch (IOException | TemplateException e) {
            log.error(e.toString());
        }
        return "";
    }
}
