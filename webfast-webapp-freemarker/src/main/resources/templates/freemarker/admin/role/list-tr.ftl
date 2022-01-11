<tr id="role-tr-${role.id}">
    <td>${role.label}</td>
    <td>${role.roleName}</td>
    <td>${role.data!}</td>
    <td>${role.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
        <div class="btn-group">
            <button class="btn btn-default btn-sm" data-url="${ctx}/admin/role/${role.id}" data-toggle="modal"
                    data-target="#modal">查看
            </button>
            <#assign buildInRole = dict['userRole']!{}/>
            <#if !buildInRole?keys?seq_contains(role.roleName)>
                <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#modal" data-toggle="modal" data-url="${ctx}/admin/role/${role.id}/edit">编辑角色信息</a>
                    </li>
                    <li><a href="javascript:" data-role="delete-item" data-url="${ctx}/admin/role/${role.id}/delete">删除角色信息</a>
                    </li>
                </ul>
            </#if>
        </div>
    </td>
</tr>