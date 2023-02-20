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

<#macro radios name choices checked = NULL!>
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

<#function file_path uri default='' absolute=false>
    <#return webExtPack.getFilePath(uri, default, absolute)/>
</#function>

<#function file_url uri default='' absolute=false>
    <#return webExtPack.getFileUrl(uri, default, absolute)/>
</#function>

<#function system_default_path category needSystemDefault=false>
    <#return webExtPack.getSystemDefaultPath(category, needSystemDefault)/>
</#function>

<#function setting name defaultValue='null'>
    <#if defaultValue != 'null'>
        <#return webExtPack.getSetting(name, defaultValue)/>
    <#else>
        <#return webExtPack.getSetting(name)/>
    </#if>
</#function>

<#function csrf_token intention='site'>
    <#return webExtPack.renderCsrfToken(intention)/>
</#function>

<#function upload_max_filesize>
    <#return (appConst.uploadMaxFilesize)! />
</#function>

<#function data key arguments = {'count': 5}>
    <#return webExtPack.getDataTag(key, arguments)/>
</#function>

<#function is_granted roleName>
    <#if userAcl??>
        <#return userAcl.hasRole(roleName) />
    <#else>
        <#return false />
    </#if>
</#function>

<#function convertIP ipAddress>
    <#return webExtPack.getConvertIp(ipAddress) />
</#function>

<#function blur_phone_number phoneNum>
    <#local len = phoneNum?length />
    <#if len lt 8><#return phoneNum/></#if>
    <#local head = phoneNum[0..3] />
    <#local endPos = len - 1/>
    <#local startPos = endPos - 3 />
    <#local tail = phoneNum[startPos..endPos] />
    <#return head + '****' + tail />
</#function>