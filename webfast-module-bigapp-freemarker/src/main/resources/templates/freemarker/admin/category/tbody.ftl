<tbody>
<#list categories! as category>
<tr id="category-tr-${category.id}">
    <td>
        <#list 0..(category.depth-1)*2 as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>└─ ${category.name}
    </td>
    <td>${category.code}</td>
    <td>${category.weight}</td>
    <td>
        <a href="javascript:;" class="btn btn-default btn-sm " data-url="${ctx}/admin/category/${category.id}/edit" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-edit"></i> 编辑</a>
        <#if group.depth gt category.depth>
        <a href="javascript:;" class="btn btn-default btn-sm" data-url="${ctx}/admin/category/create?parentId=${category.id}&groupId=${group.id}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-plus"></i> 添加子分类</a>
        </#if>

    </td>
</tr>
<#else>
<tr><td colspan="20"><div class="empty">暂无分类记录</div></td></tr>
</#list>
</tbody>