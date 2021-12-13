<#assign nav = 'content'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>

    <div class="list-group">
        <a href="${ctx}/admin/navigation" class="list-group-item <#if menu! == 'navigation'>active</#if>">导航管理</a>
        <a href="${ctx}/admin/content?type=page" class="list-group-item <#if menu! == 'content'>active</#if>">页面管理</a>
        <a href="${ctx}/admin/block" class="list-group-item <#if menu! == 'admin_block'>active</#if>">编辑区管理</a>
        <a href="${ctx}/admin/file" class="list-group-item <#if menu! == 'file'>active</#if>" style="display:none;">文件监控</a>
        <a href="${ctx}/admin/comment" class="list-group-item <#if menu! == 'comment'>active</#if>" style="display:none;">评论管理</a>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">资讯</div>
        <div class="list-group">
            <a href="${ctx}/admin/article" class="list-group-item <#if menu! == 'article'>active</#if>">资讯管理</a>
            <a href="${ctx}/admin/article/category" class="list-group-item <#if menu! == 'category'>active</#if>">栏目管理</a>
            <a href="${ctx}/admin/article/setting" class="list-group-item <#if menu! == 'setting'>active</#if>">资讯频道设置</a>
            <a class="list-group-item" href="${ctx}/article_show" target="_blank">资讯频道首页</a>
        </div>
    </div>

    </#if>
</div>
<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>