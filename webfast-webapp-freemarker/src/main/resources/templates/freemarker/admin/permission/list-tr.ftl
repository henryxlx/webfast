<tr id="perm-tr-${perm.id}">
    <td>${perm.id}</td>
    <td>${perm.permToken}</td>
    <td>${perm.description}</td>
    <td>
        <button class="btn btn-default btn-sm" data-url="${ctx}/admin/role/update/${perm.id}" data-toggle="modal" data-target="#modal">编辑</button>
    </td>
</tr>