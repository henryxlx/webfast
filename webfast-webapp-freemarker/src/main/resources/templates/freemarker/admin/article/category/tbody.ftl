<tbody>
  	<#list categories! as category>
		<tr id="category-tr-{{ category.id }}">
		  <td>
		    {% for i in 0..(category.depth-1)*2 %}&nbsp;&nbsp;&nbsp;&nbsp;{% endfor %}└─  <a href="{{ path('article_category',{ categoryCode:category.code })}}">
		    	{{ category.name }}
			</a>
		  </td>
		  <td>{{ category.code }}</td>
		  <td>{{ category.weight }}</td>
		  <td>
		    <a href="javascript:;" class="btn btn-default btn-sm" data-url="{{ path('admin_article_category_edit', {id:category.id}) }}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-edit"></i> 编辑</a>
		  	{% if category.depth < 5 %}
	      	<a href="javascript:;" class="btn btn-default btn-sm" data-url="{{ path('admin_article_category_create', {parentId:category.id}) }}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-plus"></i> 添加子栏目</a>
	      	{% endif %}
		  </td>
		</tr>
	<#else>
	  	<tr><td colspan="20"><div class="empty">暂无分类记录</div></td></tr>
	</#list>
</tbody>