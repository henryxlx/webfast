<tr id="perm-tr-${perm.id}">
    <td>${perm.id}</td>
    <td>${perm.permissionKey}</td>
    <td>${perm.label}</td>
    <td>${perm.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
        <div class="btn-group">
            <button class="btn btn-default btn-sm" data-url="${ctx}/admin/permission/${perm.id}/edit" data-toggle="modal" data-target="#modal">编辑</button>
            <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href="javascript:" data-role="delete-item" data-url="${ctx}/admin/permission/${perm.id}/delete">删除权限信息</a>
                </li>
            </ul>
        </div>
    </td>
</tr>