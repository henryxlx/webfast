package com.jetwinner.webfast.freemarker.ext;

import com.jetwinner.util.EasyStringUtil;
import com.jetwinner.webfast.freemarker.FreeMarkerSharedVariableRegister;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulixin
 */
public class BaseFreeMarkerSharedVariableRegister implements FreeMarkerSharedVariableRegister {

    protected Map<String, TemplateMethodModelEx> sharedVariableMap;

    @Override
    public Map<String, TemplateMethodModelEx> getSharedVariables() {
        return this.sharedVariableMap;
    }

    public BaseFreeMarkerSharedVariableRegister() {
        this.sharedVariableMap = new HashMap<>(1);
        plainTextFilter();
    }

    protected void validFilterArguments(List args, int count, String filterName) throws TemplateModelException {
        if (args.size() != count) {
            throw new TemplateModelException("Wrong arguments of custom FreeMarker template function: " + filterName);
        }
    }

    private void plainTextFilter() {
        String filterName = "plain_text";
        this.sharedVariableMap.put(filterName, args -> {
            validFilterArguments(args, 2, filterName);
            SimpleScalar scalar = (SimpleScalar) args.get(0);
            String content = scalar.getAsString();
            SimpleNumber number = (SimpleNumber) args.get(1);
            int len = number.getAsNumber().intValue();
            if (EasyStringUtil.isNotBlank(content)) {
                content = EasyStringUtil.plainTextFilter(content, len);
            }
            return content;
        });
    }
}
