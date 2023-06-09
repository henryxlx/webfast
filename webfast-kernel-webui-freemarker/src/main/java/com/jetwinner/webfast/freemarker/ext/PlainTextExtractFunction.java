package com.jetwinner.webfast.freemarker.ext;

import com.jetwinner.util.EasyStringUtil;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author xulixin
 */
public class PlainTextExtractFunction implements TemplateMethodModelEx {

    public static final String MODEL_VAR_NAME = "plain_text";

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments of custom FreeMarker template function: " + MODEL_VAR_NAME);
        }
        SimpleScalar scalar = (SimpleScalar) args.get(0);
        String content = scalar.getAsString();
        SimpleNumber number = (SimpleNumber) args.get(1);
        int len = number.getAsNumber().intValue();
        if (EasyStringUtil.isNotBlank(content)) {
            content = EasyStringUtil.plainTextFilter(content, len);
        }
        return content;
    }
}