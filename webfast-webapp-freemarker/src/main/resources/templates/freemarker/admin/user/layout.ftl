<#assign nav = 'user'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>
    <div class="list-group">
        <a href="${ctx}/admin/permission" class="list-group-item <#if menu! == 'permission'>active</#if>">权限管理</a>

        <a href="${ctx}/admin/role" class="list-group-item <#if menu! == 'role'>active</#if>">角色管理</a>

        <a href="${ctx}/admin/user" class="list-group-item <#if menu! == 'user'>active</#if>">用户管理</a>

        <a href="${ctx}/admin/approval/approving" class="list-group-item <#if menu! == 'approval'>active</#if>">实名认证</a>

        <a href="${ctx}/admin/message" class="list-group-item <#if menu! == 'message'>active</#if>">私信管理</a>

        <a href="${ctx}/admin/login/record" class="list-group-item <#if menu! == 'login_record'>active</#if>">用户登录日志</a>
    </div>
    </#if>
</div>
<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>
