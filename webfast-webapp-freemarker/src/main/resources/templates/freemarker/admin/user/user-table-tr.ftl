<tr id="user-table-tr-${user.id}">
    <td>
        <strong><@admin_macro.user_link user /></strong>
        <#if user.locked gt 0>
        <label class="label label-danger">禁</label>
        </#if>

        <br>
        <span class="text-muted text-sm">
            <#list roles! as role>
                <#if user.roles?contains(role.roleName)>
                    role.label
                </#if>
            </#list>
    </span>
    </td>

    <td>
        ${user.email!}
        <br>
        <#if user.emailVerified gt 0>
        <label class="label label-success" title="该Email地址已验证">已验证</label>
        </#if>
    </td>

    <td>
        <span class="text-sm">${user.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</span>
        <br>
        <span class="text-muted text-sm">
    <a class="text-muted text-sm" href="http://www.baidu.com/s?wd=${user.createdIp}" target="_blank">${user.createdIp}</a>
    ${convertIP(user.createdIp)}
  </span>
        <span></span>
    </td>
    <td>
    <span class="text-sm">
      <#if user.loginTime == 0>
       --
      <#else>
        ${user.loginTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}
      </#if>
    </span>
        <br>
        <span class="text-muted text-sm">
    <a class="text-muted text-sm" href="http://www.baidu.com/s?wd=${user.loginIp}" target="_blank">${user.loginIp}</a>
    ${convertIP(user.loginIp)}
  </span>
    </td>
    <td>
        <div class="btn-group">
            <a href="#modal" data-toggle="modal" data-url="${ctx}/admin/user/${user.id}" data-url="" class="btn btn-default btn-sm">查看</a>
            <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#modal" data-toggle="modal" data-url="${ctx}/user/${user.id}/edit">编辑用户信息</a></li>

                <#if !userAcl.hasRole('ROLE_SUPER_ADMIN')>
                <#if user.roles?contains('ROLE_SUPER_ADMIN')>
                <li></li>
                <#else>
                <li><a href="#modal" data-toggle="modal" data-url="${ctx}/admin/user/${user.id}/roles">设置用户组</a></li>
                </#if>
                <#else>
                <li><a href="#modal" data-toggle="modal" data-url="${ctx}/admin/user/${user.id}/roles">设置用户组</a></li>
                </#if>

                <#if userAcl.hasRole('ROLE_SUPER_ADMIN')>

                <li><a href="#modal" data-toggle="modal" data-url="${ctx}/admin/user/${user.id}/avatar">修改用户头像</a></li>

                <li><a href="#modal" data-toggle="modal" data-url="${ctx}/admin/user/${user.id}/change-password">修改密码</a></li>

                </#if>

                <li><a class="send-passwordreset-email" href="javascript:" data-url="${ctx}/admin/user/${user.id}/send-passwordreset-email">发送密码重置邮件</a></li>

                <li><a class="send-emailverify-email" href="javascript:" data-url="${ctx}/admin/user/${user.id}/send-emailverify-email', {id:user.id}) }}">发送Email验证邮件</a></li>
                <#if user.locked gt 0>
                <li><a class="unlock-user" href="javascript:" title="解禁用户${user.username}" data-url="${ctx}/admin/user/${user.id}/unlock">解禁用户</a></li>
                <#else>
                <#if !userAcl.hasRole('ROLE_SUPER_ADMIN')>
                <#if user.roles?contains('ROLE_SUPER_ADMIN')>
                <li></li>
                <#else>
                <li><a class="lock-user" href="javascript:" title="封禁用户${user.username}" data-url="${ctx}/admin/user/${user.id}/lock">封禁用户</a></li>
                </#if>
                <#else>
                <li><a class="lock-user" href="javascript:" title="封禁用户${user.username}" data-url="${ctx}/admin/user/${user.id}/lock">封禁用户</a></li>
                </#if>
                </#if>
            </ul>
        </div>
    </td>
</tr>