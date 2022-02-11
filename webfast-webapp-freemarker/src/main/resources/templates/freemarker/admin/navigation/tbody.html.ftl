<tbody data-update-seqs-url="${ctx}/admin/navigation/seqs-update">
<#list navigations! as navigation>
<tr class="<#if navigation.parentId == 0> has-subItems <#else> child </#if>" id="navigations-tr-${navigation.id}" data-id="${navigation.id}" data-parent-id="${navigation.parentId}">
    <td style="vertical-align: middle;">
        <#if navigation.parentId == 0>
        <span class="glyphicon glyphicon-resize-vertical"></span>
        </#if>
        <#if navigation.parentId gt 0><span class="indentation">&nbsp;&nbsp;&nbsp;&nbsp; └─</span></#if>
        <a href="${ctx}/${navigation.url!navigation_url!}" target="_blank"> ${navigation.name} </a>
    </td>
    <td>
        <#if navigation.isNewWin == 0>否<#else>是</#if>
    </td>
    <td>
        <#if navigation.isOpen == 1>开启<#else>关闭</#if>
    </td>
    <td>
        <#if navigation.type == 'top' && navigation.parentId == 0>
        <button class="btn btn-sm btn-default edit-btn" data-url="${ctx}/admin/navigation/create?type=${navigation.type}&parentId=${navigation.id}" data-toggle="modal" data-target="#modal">添加二级导航</button>
        </#if>
        <button class="btn btn-sm btn-default edit-btn" data-url="${ctx}/admin/navigation/${navigation.id}/update" data-toggle="modal" data-target="#modal">编辑</button>
        <button class="btn btn-sm btn-default delete-btn" data-url="${ctx}/admin/navigation/${navigation.id}/delete" data-target="${navigation.id}">删除</button>
    </td>
</tr>
<#else>
<tr><td colspan="20"><div class="empty">暂无导航记录</div></td></tr>
</#list>

</tbody>