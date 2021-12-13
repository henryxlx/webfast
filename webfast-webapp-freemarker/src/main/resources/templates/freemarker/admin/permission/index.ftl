<#assign menu = 'permission'/>
<#assign script_controller = 'permission/list'/>

<#include '/admin/user/layout.ftl'/>
<#macro blockTitle>权限管理 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <div class="page-header clearfix">
        <button class="btn btn-success btn-sm pull-right" id="add-permission-btn" data-toggle="modal" data-target="#modal" data-url="${ctx}/admin/permission/create">添加新权限</button>
        <h1 class="pull-left">权限管理</h1>
    </div>
    <@web_macro.flash_messages />
    <form id="permission-search-form" class="form-inline well well-sm" action="" method="get" novalidate>

        <div class="form-group">
            <select class="form-control" name="roles">
                <@select_options dict['userRole']!{} RequestParameters['roles']!'' '--所有角色--'/>
            </select>
        </div>

        <span class="divider"></span>

        <div class="form-group">
            <select id="keywordType" name="keywordType" class="form-control">
                <@select_options {'perm_name':'权限名', 'perm_description':'权限描述'} RequestParameters['keywordType']!'' '--关键词类型--'/>
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

    <table id="permission-table" class="table table-striped">
        <thead>
        <th width="10%">ID</th>
        <th width="30%">权限名</th>
        <th width="30%">描述</th>
        <#--<th width="20%">添加时间</th>-->
        <th width="10%">操作</th>
        </thead>
        <tbody>
        <#list permissions! as perm>
            <#include '/admin/permission/list-tr.ftl'/>
        <#else>
        <tr><td colspan="20"><div class="empty">暂无权限记录</div></td></tr>
        </#list>

        </tbody>
    </table>

    <@web_macro.paginator paginator! />
</#macro>