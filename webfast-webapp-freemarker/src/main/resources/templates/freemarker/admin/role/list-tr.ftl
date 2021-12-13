<tr id="role-tr-${role.id}">
    <td>${role.id}</td>
    <td>${role.roleName}</td>
    <td>${role.description}</td>
    <td>
        <button class="btn btn-default btn-sm" data-url="${ctx}/admin/role/${role.id}" data-toggle="modal" data-target="#modal">编辑</button>
    </td>
</tr>