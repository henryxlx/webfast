package com.jetwinner.webfast.freemarker;

import freemarker.template.TemplateMethodModelEx;

import java.util.Map;

/**
 * @author xulixin
 */
public interface FreeMarkerSharedVariableRegister {

    Map<String, TemplateMethodModelEx> getSharedVariables();
}
