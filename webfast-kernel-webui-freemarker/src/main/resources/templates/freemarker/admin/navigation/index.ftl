<#assign types = {'top':'顶部', 'foot':'底部'}/>
<#assign menu = 'navigation'/>
<#assign script_controller = 'navigation/page'/>

<#include '/admin/content/layout.ftl'/>
<#macro blockTitle>导航管理 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header clearfix">
    <button class="btn btn-success btn-sm pull-right" id="add-navigation-btn" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/navigation/create?type=${type!}">新增${(types[type])!}导航</button>
    <h1 class="pull-left">导航管理</h1>
</div>

<div data-role="navigation">
    <ul class="nav nav-tabs">
        <#list types! as key, name>
        <li <#if type! == key> class="active"</#if>><a href="${ctx}/admin/navigation?type=${key!}">${name!}导航</a></li>
        </#list>
    </ul>
</div>

<table id="navigation-table" class="table table-striped table-hover navigation-table">
    <thead>
    <tr>
        <th width="50%">名称</th>
        <th width="10%">新开窗口</th>
        <th width="10%">状态</th>
        <th width="30%">操作</th>
    </tr>
    </thead>

    <#include '/admin/navigation/tbody.html.ftl'/>

</table>

</#macro>
