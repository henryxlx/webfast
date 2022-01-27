package com.jetwinner.webfast.kernel.view;

import com.jetwinner.util.EasyStringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulixin
 */
public final class PostDataMapForm {

    public static PostDataMapForm createForm(String... includeNames) {
        PostDataMapForm form =  new PostDataMapForm();
        form.includeParameterNames = includeNames;
        return form;
    }

    private Map<String, Object> data;
    private String[] includeParameterNames;

    private PostDataMapForm() {
        // reserved.
    }

    public void bind(HttpServletRequest request) {
        this.data = new HashMap<>(request.getParameterMap().size());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            if (this.includeParameterNames != null && this.includeParameterNames.length > 0) {
                if (!EasyStringUtil.inArray(key, this.includeParameterNames)) {
                    continue;
                }
            }
            String[] values = request.getParameterValues(key);
            if (values != null && values.length > 1) {
                this.data.put(key, values);
            } else {
                String value = request.getParameter(key);
                this.data.put(key, value);
            }
        }
    }

    public boolean isValid() {
        return true;
    }

    public Map<String, Object> getData() {
        return this.data == null ? new HashMap<>(0) : this.data;
    }
}
