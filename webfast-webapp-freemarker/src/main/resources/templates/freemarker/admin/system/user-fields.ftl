<#assign submenu = 'user_fields'/>
<#assign script_controller = 'system/user-fields'/>

<#include '/admin/system/user-set-layout.ftl'/>
<#macro blockTitle>注册设置 - 用户字段自定义 - ${blockTitleParent}</#macro>

<#macro blockMainContent>
<button type="button" class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#myModal">添加字段</button>
<div class="page-header"><br><h1>用户字段自定义</h1></div>
	<table class="table table-striped table-hover" id="course-table">
		<thead>
		<tr>
			<th>字段名称</th>
			<th>类型</th>
			<th>是否显示</th>
			<th>显示序号</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
			<#include '/admin/system/user-fields.table.tr.ftl'/>
		</tbody>
	</table>

<@web_macro.flash_messages />
<#include '/admin/system/user-fields.modal.ftl' />

</#macro>