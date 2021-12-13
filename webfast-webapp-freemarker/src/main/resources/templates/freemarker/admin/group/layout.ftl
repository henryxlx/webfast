<#assign nav = 'group'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>

<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>

    <div class="list-group">

        <a href="${ctx}/admin/group/set" class="list-group-item <#if menu! == 'set'>active</#if>">小组设置</a>
        <a href="${ctx}/admin/group" class="list-group-item <#if menu! == 'group'>active</#if>">小组管理</a>
        <a href="${ctx}/admin/groupThread" class="list-group-item <#if menu! == 'thread'>active</#if>">话题管理</a>
        <a href="${ctx}/group" class="list-group-item" target="_blank">小组首页</a>

    </div>
    </#if>
</div>


<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>