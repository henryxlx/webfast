<#macro user_link user class='NULL'>
    <#if user != ''>
        <a <#if class != 'NULL'>class="${class}"</#if> href="javascript:;" role="show-user" data-toggle="modal"
           data-target="#modal" data-url="${ctx}/admin/user/${user.id}">${user.username}</a>
    <#else>
        <span class="text-muted">用户已删除</span>
    </#if>
</#macro>