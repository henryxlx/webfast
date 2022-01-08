package com.jetwinner.webfast.kernel.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class ViewRenderServiceImpl implements ViewRenderService{

    protected final freemarker.template.Configuration configuration;

    public ViewRenderServiceImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String renderView(String viewLocation, Map<String, Object> model) {
        try {
            Template t = configuration.getTemplate(viewLocation);
            StringWriter writer = new StringWriter();
            t.process(model, writer);
            writer.close();
            return writer.toString();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return "";
    }
}
