<#include '/user/layout.ftl'/>

<#assign pageNav = type!'about'/>

<#macro blockMain>
<div class="row">
    <div class="col-md-12">
        <#if pageNav! == 'group'>
        <div class="empty">无小组信息</div>
        <#elseif pageNav! == 'favorited'>
        <div class="empty">无收藏的内容</div>
        </#if>
    </div>
</div>
</#macro>

