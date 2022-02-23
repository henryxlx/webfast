<tr id="tag-tr-${tag.id}">
    <td>${tag.id}</td>
    <td>${tag.name}</td>
    <td>${tag.createdTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
        <button class="btn btn-default btn-sm" data-url="${ctx}/admin/tag/${tag.id}/update" data-toggle="modal" data-target="#modal">编辑</button>
    </td>
</tr>