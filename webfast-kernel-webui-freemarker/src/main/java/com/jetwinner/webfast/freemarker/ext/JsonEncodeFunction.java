package com.jetwinner.webfast.freemarker.ext;

import com.jetwinner.util.JsonUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

import java.util.List;

/**
 * @author xulixin
 */
public class JsonEncodeFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List args) throws TemplateModelException {
        if(args.size() != 1){
            throw new TemplateModelException("Wrong arguments");
        }
        final Object unwrapped = DeepUnwrap.unwrap((TemplateModel) args.get(0));
        return JsonUtil.objectToString(unwrapped);
    }
}
