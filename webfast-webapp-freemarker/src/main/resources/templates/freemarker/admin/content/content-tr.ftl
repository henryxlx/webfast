<tr id="content-${content.id}">
    <td>
        <a href="${ctx}/${(content.type)!'content'}/${(content.alias)!content.id}">${content.title}</a><br />
        <#if content.type == 'page'>
        <span class="text-muted text-sm">地址：page/${(content.alias)!content.id}</span>
        </#if>
        <#if category?? && category != 'N/A'>
        <span class="text-muted text-sm">分类：${(category.name)!}</span>
        </#if>
    </td>
    <td>${dict_text('contentType', content.type)}</td>
    <td>${dict_text('contentStatus4html', content.status)}</td>
    <td>
        ${content.publishedTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}<br />
        ${(user.username)!}
    </td>
    <td>
        <div class="btn-group">
            <a href="#modal" data-toggle="modal" data-url="${ctx}/admin/content/${content.id}/edit" class="btn btn-default btn-sm">编辑</a>
            <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <#if content.status != 'published'>
                <li><a href="javascript:" data-role="publish-item" data-url="${ctx}/admin/content/${content.id}/publish">发布</a></li>
                </#if>
                <#if content.status != 'trash'>
                <li><a href="javascript:" data-role="trash-item" data-url="${ctx}/admin/content/${content.id}/trash">移至回收站</a></li>
                </#if>
                <#if content.status == 'trash'>
                <li><a href="javascript:" data-role="delete-item" data-url="${ctx}/admin/content/${content.id}/delete">永久删除</a></li>
                </#if>
            </ul>
        </div>
    </td>
</tr>