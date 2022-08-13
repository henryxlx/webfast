<#list fields! as field>
		<tr id="field-tr-${field.id}">
		 <td>${field.title}</td>
		 <td>${field.fieldName}</td>
		 <td><#if field.enabled?? && field.enabled == 1>是<#else>否</#if></td>
		 <td>${field.seq}</td>
		 <td>${field.createdTime?number_to_date?string('yyyy-MM-dd HH:mm:ss')}</td>
		 <td><a type="button" class="btn btn-default btn-sm " href="javascript:;" data-url="${ctx}/admin/user-fields/edit/${field.id}" data-toggle="modal"  data-target="#modal">编辑</a>&nbsp;<a type="button" class="btn btn-default btn-sm " href="javascript:;" data-url="${ctx}/admin/user-fields/delete/${field.id}" data-toggle="modal"  data-target="#modal">删除</a></td>
		</tr>
<#else>
	  <tr><td colspan="20"><div class="empty">暂无自定义字段</div></td></tr>
</#list>
