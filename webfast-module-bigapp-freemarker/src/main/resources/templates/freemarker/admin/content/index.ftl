<#assign menu = 'content'/>
<#assign script_controller = 'content/list'/>

<@block_title '内容管理'/>

<#include '/admin/content/layout.ftl'/>

<#macro blockMain>

    <#assign type = RequestParameters['type']!'page' />
    <#assign typeName = (dict['contentType'][type])! />


    <div class="page-header clearfix">
    <button class="btn btn-success btn-sm pull-right" id="content-add-btn" type="button" data-target="#modal" data-toggle="modal" data-url="${ctx}/admin/content/create?type=${type}"><span class="glyphicon glyphicon-plus"></span> 添加${typeName!}</button>
    <h1 class="pull-left">${typeName!}管理</h1>
</div>

<form class="well well-sm form-inline">
    <div class="form-group">
        <select class="form-control" name="status">
            <@select_options dict['contentStatus']!{} RequestParameters['status']!'' '--所有状态--' />
        </select>
    </div>
    <div class="form-group">
        <input class="form-control" name="keywords" type="text" placeholder="关键词" value="${RequestParameters['keywords']!}">
    </div>
    <input type="hidden" name="type" value="${type}">
    <button class="btn btn-primary" type="submit">搜索</button>
</form>

<table class="table table-hover" id="content-table">
    <thead>
    <tr>
        <th width="50%">标题</th>
        <th>类型</th>
        <th>状态</th>
        <th>发布时间/人</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list contents! as content>
        <#assign user = (users[content.userId?c])! />
        <#assign category = (categories[content.categoryId?c])!'N/A'/>
        <#include '/admin/content/content-tr.ftl'/>
    <#else>
    <tr><td colspan="20"><div class="empty">暂无页面记录</div></td></tr>
    </#list>
    </tbody>
</table>

<@web_macro.paginator paginator! />

</#macro>