<tbody>
<#list categories! as category>
<tr id="category-tr-{{ category.id }}">
    <td>
        {% for i in 0..(category.depth-1)*2 %}&nbsp;&nbsp;&nbsp;&nbsp;{% endfor %}└─ {{ category.name }}
    </td>
    <td>{{ category.code }}</td>
    <td>{{ category.weight }}</td>
    <td>
        <a href="javascript:;" class="btn btn-default btn-sm " data-url="{{ path('admin_category_edit', {id:category.id}) }}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-edit"></i> 编辑</a>
        {% if group.depth > category.depth %}
        <a href="javascript:;" class="btn btn-default btn-sm" data-url="{{ path('admin_category_create', {parentId:category.id, groupId:group.id}) }}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-plus"></i> 添加子分类</a>
        {% endif %}

    </td>
</tr>
<#else>
<tr><td colspan="20"><div class="empty">暂无分类记录</div></td></tr>
</#list>
</tbody>