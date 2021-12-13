<#assign menu = 'admin_block'/>
<#assign script_controller = 'block/list'/>

<#include '/admin/content/layout.ftl'/>
<#macro blockTitle>编辑区管理 - ${blockTitleParent}</#macro>

<#macro blockMain>
<div class="page-header">
    <#if setting('developer.debug')??>
    <button class="btn btn-info btn-sm pull-right" id="add-block-btn" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/block/create">新增编辑区</button>
    </#if>
    <h1>编辑区管理</h1>
</div>

<table id="block-table" class="table table-striped">
    <thead>
    <tr>
        <th width="50%">标题</th>
        <th width="25%">最近更新</th>
        <th width="25%">操作</th>
    </tr>
    </thead>
    <tbody>
    <#list blocks! as block>
    <#include '/admin/block/list-tr.ftl'/>
    <#else>
    <tr><td colspan="20"><div class="empty">暂无编辑区记录</div></td></tr>
    </#list>
    </tbody>
</table>

<@web_macro.paginator paginator!/>
</#macro>