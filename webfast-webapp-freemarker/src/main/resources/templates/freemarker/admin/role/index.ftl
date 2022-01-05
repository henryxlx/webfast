<#assign menu = 'role'/>
<#assign script_controller = 'role/list'/>

<#include '/admin/user/layout.ftl'/>
<#macro blockTitle>角色管理 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <div class="page-header clearfix">
        <button class="btn btn-success btn-sm pull-right" id="add-role-btn" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/role/create">添加新角色</button>
        <h1 class="pull-left">角色管理</h1>
    </div>
    <@web_macro.flash_messages />

    <table id="role-table" class="table table-striped">
        <thead>
        <th width="10%">角色名</th>
        <th width="30%">编码</th>
        <th width="30%">权限</th>
        <th width="20%">添加时间</th>
        <th width="10%">操作</th>
        </thead>
        <tbody>
        <#list roles! as role>
            <#include '/admin/role/list-tr.ftl' />
        <#else>
            <tr><td colspan="20"><div class="empty">暂无角色记录</div></td></tr>
        </#list>

        </tbody>
    </table>
    <@web_macro.paginator paginator!/>

    <div class="alert alert-info" role="alert">
        <p>1、初始化的4个角色，无法编辑和删除</p>
        <p>2、角色创建成功后编码不可修改</p>
    </div>
</#macro>