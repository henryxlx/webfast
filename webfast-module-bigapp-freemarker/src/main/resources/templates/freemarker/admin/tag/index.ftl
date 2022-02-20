<#assign menu = 'tag'/>
<#assign script_controller = 'tag/list'/>

<#include '/admin/course/layout.ftl'/>
<#macro blockTitle>标签管理 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header clearfix">
    <button class="btn btn-success btn-sm pull-right" id="add-tag-btn" data-toggle="modal" data-target="#modal" data-url="{{ path('admin_tag_create') }}">新增标签</button>
    <h1 class="pull-left">标签管理</h1>
</div>

<table id="tag-table" class="table table-striped">
    <thead>
    <th width="10%">ID</th>
    <th width="60%">名称</th>
    <th width="20%">添加时间</th>
    <th width="10%">操作</th>
    </thead>
    <tbody>
    <#list tags! as tag>
        <#include '/admin/tag/list-tr.ftl'/>
    <#else>
    <tr><td colspan="20"><div class="empty">暂无标签记录</div></td></tr>
    </#list>

    </tbody>
</table>

<@web_macro.paginator paginator!/>


</#macro>