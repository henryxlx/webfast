<tr id="content-${content.id}">
    <td>
        <a href="{{ path('content_' ~ content.type ~ '_show', {alias:content.alias|default(content.id)}) }}" target="_blank">${content.title}</a><br />
        <#if content.type == 'page'>
        <span class="text-muted text-sm">地址：page/{{ content.alias|default(content.id) }}</span>
        </#if>
        <#if category??>
        <span class="text-muted text-sm">分类：{{ category.name }}</span>
        </#if>
    </td>
    <td>{{ dict_text('contentType', content.type) }}</td>
    <td>{{ dict_text('contentStatus:html', content.status) }}</td>
    <td>
        ${content.publishedTime?number_to_datetime?string('yyyy-MM-dd HH:mm:ss')}<br />
        {{ user.nickname }}
    </td>
    <td>
        <div class="btn-group">
            <a href="#modal" data-toggle="modal" data-url="{{ path('admin_content_edit', {id:content.id}) }}" class="btn btn-default btn-sm">编辑</a>
            <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <#if content.status != 'published'>
                <li><a href="javascript:" data-role="publish-item" data-url="{{ path('admin_content_publish', {id:content.id}) }}">发布</a></li>
                </#if>
                <#if content.status != 'trash'>
                <li><a href="javascript:" data-role="trash-item" data-url="{{ path('admin_content_trash', {id:content.id}) }}">移至回收站</a></li>
                </#if>
                <#if content.status == 'trash'>
                <li><a href="javascript:" data-role="delete-item" data-url="{{ path('admin_content_delete', {id:content.id}) }}">永久删除</a></li>
                </#if>
            </ul>
        </div>
    </td>
</tr>