package com.jetwinner.webfast.freemarker.ext;

import com.jetwinner.webfast.kernel.FastDataDictHolder;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author xulixin
 * The advantage of TemplateMethodModelEx is that it accepts non-String method arguments.
 * TemplateMethodModel has remained from the old times (1.x?) when FreeMarker only had string values.
 * 自定义FreeMarker模板页面函数dict_text，获取多个字典项后，按指定的键值显示其中的键值
 * 使用方法: ${dict_text(type, key)}, type是包含多个备选项的字典名称类型， key指定某个备选项的主键，函数返回字典项的值
 */
public class DictTextFunction implements TemplateMethodModelEx {

    public static final String MODEL_VAR_NAME = "dict_text";

    private final FastDataDictHolder dictHolder;

    public DictTextFunction(FastDataDictHolder dictHolder) {
        this.dictHolder = dictHolder;
    }

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments of custom FreeMarker template function: " + MODEL_VAR_NAME);
        }
        SimpleScalar simpleScalar = (SimpleScalar) args.get(0);
        String type = simpleScalar.getAsString();
        simpleScalar = (SimpleScalar) args.get(1);
        String key = simpleScalar.getAsString();
        return dictHolder.text(type, key);
    }
}
