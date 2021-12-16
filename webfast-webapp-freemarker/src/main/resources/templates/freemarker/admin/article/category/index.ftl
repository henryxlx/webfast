<#assign menu = 'category'/>

<#include '/admin/content/layout.ftl'/>
<#macro blockTitle>栏目管理 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header clearfix">
    <button class="btn btn-sm btn-success pull-right add-category" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/article/category/create">新建栏目</button>
    <h1 class="pull-left">栏目管理</h1>
</div>

<table class="table table-hover" id="category-table">
    <thead>
    <tr>
        <th width="45%">栏目名称</th>
        <th width="20%">编码</th>
        <th width="10%">显示序号</th>
        <th width="20%">操作</th>
    </tr>
    </thead>
    <#include '/admin/article/category/tbody.ftl'/>
</table>
</#macro>