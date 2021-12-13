<#assign nav = 'app'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>

    <div class="panel panel-default">
        <div class="panel-heading">应用管理</div>
        <div class="list-group">
            <a href="${ctx}/admin/app/center" class="list-group-item <#if menu! == 'center'>active</#if>">应用中心</a>
            <a href="${ctx}/admin/app/installed" class="list-group-item <#if menu! == 'installed'>active</#if>">已安装应用</a>
            <a href="${ctx}/admin/app/upgrades" class="list-group-item <#if menu! == 'upgrades'>active</#if>">检查应用更新</a>
            <a href="${ctx}/admin/app/logs" class="list-group-item <#if menu! == 'logs'>active</#if>">应用更新日志</a>
        </div>

    </div>

    </#if>
</div>
<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>