<#macro flash_messages>
    <#list Session.flashbag! as type, flashMessages>
        <#list flashMessages! as flashMessage>
            <div class="alert alert-${type!}">${flashMessage!}</div>
        </#list>
    </#list>
</#macro>

<#macro bytesToSize bytes>
    <#assign kilobyte = 1024/>
    <#assign megabyte = kilobyte * 1024/>
    <#assign gigabyte = megabyte * 1024/>
    <#assign terabyte = gigabyte * 1024/>

    <#if bytes < kilobyte>
        ${bytes} B
    <#elseif bytes < megabyte>
        ${(bytes / kilobyte)?string["0.##"]} KB
    <#elseif bytes < gigabyte>
        ${(bytes / megabyte)?string["0.##"]} MB
    <#elseif bytes < terabyte>
        ${(bytes / gigabyte)?string["0.##"]} GB
    <#else>
        ${(bytes / terabyte)?string["0.##"]} TB
    </#if>
</#macro>

<#macro paginator pagingObj cssClass = ''>
<#if (pagingObj.lastPage)?? && pagingObj.lastPage gt 1 >
<ul class="pagination ${cssClass}">
    <#if pagingObj.currentPage != pagingObj.firstPage>
    <li><a  href="${pagingObj.getPageUrl(pagingObj.firstPage)}">首页</a></li>
    <li><a  href="${pagingObj.getPageUrl(pagingObj.previousPage)}">上一页</a></li>
    </#if>
    <#list pagingObj.pages as page>
    <li <#if page == pagingObj.currentPage>class="active"</#if>><a href="${pagingObj.getPageUrl(page)}">${page!}</a></li>
    </#list>

    <#if pagingObj.currentPage != pagingObj.lastPage>
    <li><a  href="${pagingObj.getPageUrl(pagingObj.nextPage)}">下一页</a></li>
    <li><a  href="${pagingObj.getPageUrl(pagingObj.itemCount)}">尾页</a></li>
    </#if>
</ul>
</#if>
</#macro>

<#function toJsonText object>
    <#if object??>
        <#if object?is_enumerable>
            <#local json = '['>
            <#list object as item>
                <#if item?is_hash>
                    <#if item_index &gt; 0 && json != "[" >
                        <#local json = json +',' >
                    </#if>
                    <#local json = json + toJsonText(item)>
                </#if>
            </#list>
            <#return json + ']'>
        <#elseif object?is_hash>
            <#local json = "{">
            <#assign keys = object?keys>
            <#list keys as key>
                <#if object[key]?? && !(object[key]?is_method) && key != "class">
                    <#if object[key]?is_number>
                        <#if key_index &gt; 0 && json != "{" >
                            <#local json = json +',' >
                        </#if>
                        <#local json = json + '"${key}": ${object[key]}'>
                    <#elseif object[key]?is_string>
                        <#if key_index &gt; 0 && json != "{" >
                            <#local json = json +',' >
                        </#if>
                        <#local json = json + '"${key}": "${object[key]?html!""?js_string}"'>
                    <#elseif object[key]?is_boolean >
                        <#if key_index &gt; 0 && json != "{" >
                            <#local json = json +',' >
                        </#if>
                        <#local json = json + '"${key}": ${object[key]?string("true", "false")}'>
                    <#elseif object[key]?is_enumerable >
                        <#if key_index &gt; 0 && json != "{" >
                            <#local json = json +',' >
                        </#if>
                        <#local json = json + '"${key}":'+ toJsonText(object[key])>
                    <#elseif object[key]?is_hash>
                        <#if key_index &gt; 0 && json != "{" >
                            <#local json = json +',' >
                        </#if>
                        <#local json = json + '"${key}":'+ toJsonText(object[key])>
                    </#if>
                </#if>
            </#list>
            <#return json +"}">
        </#if>
    <#else>
        <#return "{}">
    </#if>
</#function>