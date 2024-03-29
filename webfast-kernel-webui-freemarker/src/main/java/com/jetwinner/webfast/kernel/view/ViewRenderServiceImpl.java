package com.jetwinner.webfast.kernel.view;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author xulixin
 */
@Component
public class ViewRenderServiceImpl implements ViewRenderService {

    private static final Logger log = LoggerFactory.getLogger(ViewRenderServiceImpl.class);

    private final ViewReferenceFacade viewReferenceFacade;

    protected final freemarker.template.Configuration configuration;

    public ViewRenderServiceImpl(ViewReferenceFacade viewReferenceFacade, Configuration configuration) {
        this.viewReferenceFacade = viewReferenceFacade;
        this.configuration = configuration;
    }

    @Override
    public String renderView(HttpServletRequest request, String viewLocation, Map<String, Object> model) {
        return renderView(request, viewLocation, model, null);
    }

    @Override
    public String renderView(HttpServletRequest request, String viewLocation, Map<String, Object> model,
                             Map<String, Object> importMacroModel) {

        model.putAll(viewReferenceFacade.getViewSharedVariable(request));
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
