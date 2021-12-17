<#list fields! as field>
		<tr id="field-tr-{{ field.id }}">
		 <td>{{field.title}}</td>
		 <td>{{field.fieldName}}</td>
		 <td>{% if field.enabled %}是{% else %}否{% endif %}</td>
		 <td>{{field.seq}}</td>
		 <td>{{field.createdTime|date('Y-m-d H:i:s')}}</td>
		 <td><a type="button" class="btn btn-default btn-sm " href="javascript:;" data-url="{{url('admin_user_fields_edit',{id:field.id})}}" data-toggle="modal"  data-target="#modal">编辑</a>&nbsp;<a type="button" class="btn btn-default btn-sm " href="javascript:;" data-url="{{url('admin_user_fields_delete',{id:field.id})}}" data-toggle="modal"  data-target="#modal">删除</a></td>
		</tr>
<#else>
	  <tr><td colspan="20"><div class="empty">暂无自定义字段</div></td></tr>
</#list>
