<tr id="article-${article.id}">
	<td><input value="${article.id}" type="checkbox" data-role="batch-item" > ${article.id}</td>
	<td>
		<span class="text-muted"><a href="${ctx}/article/${article.id}" target="_blank">${article.title[0..*22]}<#if article.title?length gt 22>...</#if></a></span>
	</td>
	<td>
	<#if category??>
		<span class="text-muted"><a href="${ctx}/article/category/${category.code}" target="_blank">${category.name}</a></span>
	<#else>
		<span class="text-muted">未分类</span>
	</#if>
	</td>
	<td>${article.updatedTime?number_to_datetime?string('yyyy-MM-dd HH:mm')}</td>
	<td>
	<a href="javascript:;" class="featured-label" data-set-url="${ctx}/article/${article.id}/property/set/featured" data-cancel-url="${ctx}/article/${article.id}/property/cancel/featured"><#if article.featured == 1><span class="label label-success"><#else><span class="label label-default"></#if>头</span></a>

	<a href="javascript:;" class="promoted-label" data-set-url="${ctx}/article/${article.id}/property/set/promoted" data-cancel-url="${ctx}/article/${article.id}/property/cancel/promoted"><#if article.promoted == 1><span class="label label-success"><#else><span class="label label-default"></#if>荐</span></a>

	<a href="javascript:;" class="sticky-label" data-set-url="${ctx}/article/${article.id}/property/set/sticky" data-cancel-url="${ctx}/article/${article.id}/property/cancel/sticky"><#if article.sticky == 1><span class="label label-success"><#else><span class="label label-default"></#if>顶</span></a>
	</td>

	<td>${dict_text('articleStatus:html', article.status)}</td>

	<td>
		<div class="btn-group">
		  <a  href="${ctx}/admin/article/${article.id}/edit" class="btn btn-default btn-sm" >编辑</a>
		  <a href="#" type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
		    <span class="caret"></span>
		  </a>
		  <ul class="dropdown-menu">
		  	<#if article.status != 'published'>
			    <li><a href="javascript:" data-role="publish-item" data-url="${ctx}/admin/article/${article.id}/publish">发布</a></li>
		    </#if>
	     	<#if article.status == 'published'>
			    <li><a href="javascript:" data-role="unpublish-item" data-url="${ctx}/admin/article/${article.id}/unpublish">取消发布</a></li>
		    </#if>
		    <#if article.status != 'trash'>
			    <li><a href="javascript:" data-role="trash-item" data-url="${ctx}/admin/article/${article.id}/trash">移至回收站</a></li>
			</#if>
		    <#if article.status == 'trash'>
			    <li><a href="javascript:" data-role="delete-item" data-url="${ctx}/admin/article/${article.id}/delete">永久删除</a></li>
			</#if>
		  </ul>
		</div>
	</td>
</tr>