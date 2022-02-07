<tbody>
  	<#list categories! as category>
		<tr id="category-tr-${category.id}">
		  <td>
		    <#list 0..(category.depth-1)*2 as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>└─  <a href="${ctx}/article/category/${category.code}">
		    	${category.name}
			</a>
		  </td>
		  <td>${category.code}</td>
		  <td>${category.weight}</td>
		  <td>
		    <a href="javascript:;" class="btn btn-default btn-sm" data-url="${ctx}/admin/article/category/${category.id}/edit" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-edit"></i> 编辑</a>
		  	<#if category.depth lt 5>
	      	<a href="javascript:;" class="btn btn-default btn-sm" data-url="${ctx}/admin/article/category/create?parentId=${category.id}" data-toggle="modal" data-target="#modal"><i class="glyphicon glyphicon-plus"></i> 添加子栏目</a>
			</#if>
		  </td>
		</tr>
	<#else>
	  	<tr><td colspan="20"><div class="empty">暂无分类记录</div></td></tr>
	</#list>
</tbody>