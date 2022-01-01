<tr id="${block.id!}">
    <td>
        ${block.title!}
        <br>
        <span class="text-muted text-sm">编码：${block.code!}</span>
    </td>
    <td>
        <@admin_macro.user_link latestUpdateUser! />
        <br>
        <span class="text-muted text-sm">${block.updateTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}</span>
    </td>
    <td>
        <button class="btn btn-sm btn-primary update-btn" data-url="${ctx}/admin/block/${block.id}/update" data-toggle="modal" data-target="#modal">编辑内容</button>
        <#if setting('developer.debug')??>
        <button class="btn btn-sm btn-default edit-btn" data-url="${ctx}/admin/block/${block.id}/edit" data-toggle="modal" data-target="#modal" >设置</button>
        <button class="btn btn-sm btn-default delete-btn" data-url="${ctx}/admin/block/${block.id}/delete" data-target="${block.id}">删除</button>
        </#if>
    </td>
</tr>