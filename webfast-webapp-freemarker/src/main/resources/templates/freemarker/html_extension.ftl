<#macro select_options choices selected = 'null' empty = 'null'>
    <#if empty != 'null'>
        <option value="">${empty}</option>
    </#if>
    <#list choices! as value, name>
        <#if selected == value>
            <option value="${value}" selected="selected">${name}</option>
        <#else>
            <option value="${value}">${name}</option>
        </#if>
    </#list>
</#macro>

<#macro radios name choices checked = 'null'>
    <#list choices! as value, label>
        <#if checked == value>
            <label><input type="radio" name="${name}" value="${value!}" checked="checked">${label!}</label>
        <#else>
            <label><input type="radio" name="${name}" value="${value!}">${label!}</label>
        </#if>
    </#list>
</#macro>

<#macro checkboxs name choices checkeds = []>
    <#if checkeds?is_sequence == false>
        <#local checkeds = [checkeds] />
    </#if>
    <#list choices! as value, label>
        <#if checkeds?seq_contains(value)>
            <label><input type="checkbox" name="${name}[]" value="${value!}" checked="checked"> ${label!}</label>
        <#else>
            <label><input type="checkbox" name="${name}[]" value="${value!}"> ${label!}</label>
        </#if>
    </#list>
</#macro>

<#function default_path category uri='' size = '' absolute=false>
    <#return webExtPack.getDefaultPath(category, uri, size, absolute)/>
</#function>

<#function setting name defaultValue='null'>
    <#if defaultValue != 'null'>
        <#return webExtPack.getSetting(name, defaultValue)/>
    <#else>
        <#return webExtPack.getSetting(name)/>
    </#if>
</#function>

<#function csrf_token intention=''>
    <#return ''/>
</#function>

<#function upload_max_filesize>
</#function>

<#function data key count=5>
    <#return []/>
</#function>