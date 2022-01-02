<tr id="role-tr-${role.id}">
    <td>${role.label}</td>
    <td>${role.roleName}</td>
    <td>${role.data!}</td>
    <td>${role.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
        <button class="btn btn-default btn-sm" data-url="${ctx}/admin/role/${role.id}" data-toggle="modal" data-target="#modal">编辑</button>
    </td>
</tr>