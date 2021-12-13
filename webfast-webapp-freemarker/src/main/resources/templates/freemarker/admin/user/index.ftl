<#assign menu = 'user'/>
<#assign script_controller = 'user/list'/>

<#include '/admin/user/layout.ftl'/>
<#macro blockTitle>用户管理 - ${blockTitleParent}</#macro>

<#macro blockMain>
<div class="page-header clearfix">
    <button class="btn btn-success btn-sm pull-right" id="add-navigation-btn" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/user/create" >添加新用户</button>
    <h1 class="pull-left">用户管理</h1>
</div>
<@web_macro.flash_messages />
<form id="user-search-form" class="form-inline well well-sm" action="" method="get" novalidate>

    <div class="form-group">
        <select class="form-control" name="roles">
            <@select_options dict['userRole']!{} RequestParameters['roles']!'' '--所有角色--'/>
        </select>
    </div>

    <span class="divider"></span>

    <div class="form-group">
        <select id="keywordType" name="keywordType" class="form-control">
            <@select_options dict['userKeyWordType']!{} RequestParameters['keywordType']!'' '--关键词类型--'/>
        </select>
    </div>

    <div class="form-group">
        <input type="text" id="keyword" name="keyword" class="form-control" value="${RequestParameters['keyword']!}" placeholder="关键词">
    </div>

    <button class="btn btn-primary">搜索</button>

    <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>
    <#if showUserExport??>
    <a class="btn btn-primary mhs" id="user-export" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/user/export">导出用户</a>

    </#if>
    </#if>

</form>

<table id="user-table" class="table table-striped table-hover" data-search-form="#user-search-form">
    <thead>
    <tr>
        <th>用户名</th>
        <th>Email</th>
        <th>注册时间</th>
        <th>最近登录</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#list users! as user>
        <#include '/admin/user/user-table-tr.ftl'/>
    </#list>
    </tbody>
</table>
<@web_macro.paginator paginator!/>
</#macro>