<#assign nav = 'app'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>

    <div class="list-group">
        <a href="${ctx}/admin/navigation?layout=app" class="list-group-item <#if menu! == 'navigation'>active</#if>">导航管理</a>
        <a href="${ctx}/admin/block?layout=app" class="list-group-item <#if menu! == 'admin_block'>active</#if>">编辑区管理</a>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">应用管理</div>
        <div class="list-group">
            <a href="${ctx}/admin/app/reinstall" class="list-group-item <#if menu! == 'reinstall'>active</#if>">重新安装</a>
            <a href="${ctx}/admin/app/installed" class="list-group-item <#if menu! == 'installed'>active</#if>">已安装应用</a>
            <a href="${ctx}/admin/app/logs" class="list-group-item <#if menu! == 'logs'>active</#if>">应用更新日志</a>
        </div>

    </div>

    </#if>
</div>
<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>