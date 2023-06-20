package com.jetwinner.webfast.freemarker.tag;

import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import com.jetwinner.webfast.mvc.block.BlockRenderControllerExecutor;
import com.jetwinner.webfast.mvc.extension.WebExtensionPack;
import freemarker.core.Environment;
import freemarker.core._DelayedFTLTypeDescription;
import freemarker.core._MiscTemplateException;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author xulixin
 */
@Component
public class RenderControllerTag implements TemplateDirectiveModel {

    public static final String MODEL_VAR_NAME = "renderController";

    private static final String FREEMARKER_TEMPLATE_FILE_EXT_NAME = ".ftl";

    private final BlockRenderControllerExecutor controllerExecutor;

    public RenderControllerTag(BlockRenderControllerExecutor executor) {
        this.controllerExecutor = executor;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody)
            throws TemplateException, IOException {

        TemplateModel path = (TemplateModel) params.get("path");
        if (path == null) {
            throw new _MiscTemplateException(env, "Missing required parameter \"path\"");
        } else if (!(path instanceof TemplateScalarModel)) {
            throw new _MiscTemplateException(env, new Object[]{"Expected a scalar model. \"path\" is instead ", new _DelayedFTLTypeDescription(path)});
        } else {
            String strPath = ((TemplateScalarModel) path).getAsString();
            if (strPath == null) {
                throw new _MiscTemplateException(env, "String value of \"path\" parameter is null");
            } else {
                HttpServletRequest request = getRequestFromDataModel(env.getDataModel());
                if (request == null) {
                    throw new RuntimeGoingException("HttpServletRequest in FreeMarker Data Model is not available.");
                }

                // Get explicit params, if any
                final Map paramsMap = getExtraParameterAsMap(env, params);
                final HttpServletRequest wrappedRequest = new CustomBlockRequestWrapper(strPath, request, paramsMap, true);

                try {
                    ModelAndView mav = controllerExecutor.handleRequest(wrappedRequest);
                    if (mav != null) {
                        Map<String, Object> map = mav.getModel();
                        for (String key : map.keySet()) {
                            env.setVariable(key, getModel(map.get(key)));
                        }
                        String viewName = mav.getViewName();
                        if (!viewName.endsWith(FREEMARKER_TEMPLATE_FILE_EXT_NAME)) {
                            viewName = viewName + FREEMARKER_TEMPLATE_FILE_EXT_NAME;
                        }
                        Template template = env.getTemplateForImporting(viewName);
                        env.include(template);
                    }
                } catch (Exception e) {
                    throw new RuntimeGoingException("Can not render Controller mapping at " + e.getMessage());
                }
            }
        }
    }

    private HttpServletRequest getRequestFromDataModel(TemplateHashModel dataModel) throws TemplateModelException {
        TemplateModel requestBoxModel = dataModel.get(WebExtensionPack.MODEL_VAR_NAME);
        Object objRequestBox = DeepUnwrap.unwrap(requestBoxModel);
        if (!(objRequestBox instanceof WebExtensionPack)) {
            throw new RuntimeGoingException("Can not obtain WebExtensionPack in FreeMarker Data Model.");
        }
        HttpServletRequest request = ((WebExtensionPack) objRequestBox).getRequest();
        return request;
    }

    private Map getExtraParameterAsMap(Environment env, Map params) throws TemplateModelException, _MiscTemplateException {
        final Map paramsMap;
        final TemplateModel paramsModel = (TemplateModel) params.get("params");
        if (paramsModel != null) {
            // Convert params to a Map
            final Object unwrapped = DeepUnwrap.unwrap(paramsModel);
            if (!(unwrapped instanceof Map)) {
                throw new _MiscTemplateException(env,
                        "Expected \"params\" to unwrap into a java.util.Map. It unwrapped into ",
                        unwrapped.getClass().getName(), " instead.");
            }
            paramsMap = (Map) unwrapped;
        } else {
            paramsMap = Collections.EMPTY_MAP;
        }
        return paramsMap;
    }

    private DefaultObjectWrapper getBuilder() {
        return new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_30).build();
    }

    private TemplateModel getModel(Object o) throws TemplateModelException {
        return this.getBuilder().wrap(o);
    }

    private static final class CustomBlockRequestWrapper extends HttpServletRequestWrapper {
        private final HashMap<String, String[]> paramsMap;
        private final HashMap<String, Object> attributeMap;
        private final String lookupPath;

        private CustomBlockRequestWrapper(String lookupPath, HttpServletRequest request, Map paramMap,
                                          boolean inheritParams) {
            super(request);
            this.lookupPath = lookupPath;
            this.attributeMap = new HashMap<>();
            paramsMap = inheritParams ? new HashMap<>(request.getParameterMap()) : new HashMap<>();
            for (Iterator it = paramMap.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry) it.next();
                String name = String.valueOf(entry.getKey());
                Object value = entry.getValue();
                final String[] valueArray;
                if (value == null) {
                    // Null values are explicitly added (so, among other
                    // things, we can hide inherited param values).
                    valueArray = new String[]{null};
                } else if (value instanceof String[]) {
                    // String[] arrays are just passed through
                    valueArray = (String[]) value;
                } else if (value instanceof Collection) {
                    // Collections are converted to String[], with
                    // String.valueOf() used on elements
/*
                    Collection col = (Collection) value;
                    valueArray = new String[col.size()];
                    int i = 0;
                    for (Iterator it2 = col.iterator(); it2.hasNext(); ) {
                        valueArray[i++] = String.valueOf(it2.next());
                    }
*/
                    /* fixed
                     * HTTP Post提交List等集合数据SpringMVC映射会发生类型错误，因此将集合类型直接注入到Request的属性Attribute中
                     * 这样处理的好处是集合类型不用再进行类型转换和重新映射，直接从Request的属性中获取更高效
                     * 不足之处就是处理请求的方法不能直接明确定义集合类型，方法参数只能添加HttpServletRequest类型参数，
                     * 然后通过Request.getAttribute间接获取
                     */
                    valueArray = new String[]{null};
                    this.attributeMap.put(name, value);
                } else if (value instanceof Map) {
                    // Map类型的数据同样保存到HttpServletRequest中，使用getAttribute方法获取
                    valueArray = new String[]{null};
                    this.attributeMap.put(name, value);
                } else if (value.getClass().isArray()) {
                    // Other array types are too converted to String[], with
                    // String.valueOf() used on elements
                    int len = Array.getLength(value);
                    valueArray = new String[len];
                    for (int i = 0; i < len; ++i) {
                        valueArray[i] = String.valueOf(Array.get(value, i));
                    }
                } else {
                    // All other values (including strings) are converted to a
                    // single-element String[], with String.valueOf applied to
                    // the value.
                    valueArray = new String[]{String.valueOf(value)};
                }
                String[] existingParams = paramsMap.get(name);
                int el = existingParams == null ? 0 : existingParams.length;
                if (el == 0) {
                    // No original params, just put our array
                    paramsMap.put(name, valueArray);
                } else {
                    int vl = valueArray.length;
                    if (vl > 0) {
                        // Both original params and new params, prepend our
                        // params to original params
                        String[] newValueArray = new String[el + vl];
                        System.arraycopy(valueArray, 0, newValueArray, 0, vl);
                        System.arraycopy(existingParams, 0, newValueArray, vl, el);
                        paramsMap.put(name, newValueArray);
                    }
                }
            }
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] value = paramsMap.get(name);
            return value != null ? value.clone() : null;
        }

        @Override
        public String getParameter(String name) {
            String[] values = paramsMap.get(name);
            return values != null && values.length > 0 ? values[0] : null;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(paramsMap.keySet());
        }

        @SuppressWarnings("unchecked")
        @Override
        public Map<String, String[]> getParameterMap() {
            HashMap<String, String[]> clone = (HashMap<String, String[]>) paramsMap.clone();
            Iterator<Map.Entry<String, String[]>> it = clone.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String[]> entry = it.next();
                entry.setValue(entry.getValue().clone());
            }
            return Collections.unmodifiableMap(clone);
        }

        @Override
        public Object getAttribute(String name) {
            if (this.attributeMap.containsKey(name)) {
                return this.attributeMap.get(name);
            }
            return getRequest() != null ? getRequest().getAttribute(name) : null;
        }

        @Override
        public void setAttribute(String name, Object obj) {
            this.attributeMap.put(name, obj);
        }

        @Override
        public String getRequestURI() {
            return this.lookupPath;
        }
    }
}