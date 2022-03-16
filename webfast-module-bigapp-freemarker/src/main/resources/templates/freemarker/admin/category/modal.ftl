<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle><#if category.id??>编辑分类<#else>添加分类</#if></#macro>

<#macro blockBody>
<div id="category-creater-widget" data-upload-url="${ctx}/admin/category/upload">

	<form id="category-form" class="form-horizontal" action="<#if category.id??>${ctx}/admin/category/${category.id}/edit<#else>${ctx}/admin/category/create</#if>" method="post">
		<div class="form-group">
			<label class="col-md-2 control-label" for="category-name-field">名称</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-name-field" type="text" name="name" value="${category.name!}" tabindex="1">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-weight-field">显示序号</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-weight-field" type="text" name="weight" value="${category.weight!}" tabindex="2">
				<div class="help-block">显示序号需为整数，分类按序号的顺序从小到大排序。</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-code-field">编码</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-code-field" type="text" name="code" value="${category.code!}" data-url="${ctx}/admin/category/checkcode?exclude=${category.code}" tabindex="3">
				<div class="help-block">必填，建议使用分类名称的英文单词或缩写作为编码。</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-description-field">分类描述</label>
			<div class="col-md-8 controls">
				<textarea class="form-control" id="category-description-field" style="height:100px;" name="description">${category.description!}</textarea>
				<div class="help-block">非必填</div>
			</div>
		</div>

		<div class="form-group" style="display:none;">
			<label class="col-md-2 control-label" for="category-code-field">图标</label>
			<div class="col-md-8 controls">
				<div id="category-icon-field">
					<#if category.icon??>
						<img class="mbm" src="${category.icon!}">
					</#if>
				</div>
				<input type="hidden" name="icon" value="${category.icon!}">
				<button id="category-icon-uploader" class="btn btn-sm btn-default webuploader-container" type="button" data-target="#category-icon-field"><i class="glyphicon glyphicon-picture"></i></button>
				<button <#if !category.icon??>style="display:none;"</#if> id="category-icon-delete" class="btn btn-sm btn-default webuploader-container" type="button"><i class="glyphicon glyphicon-trash"></i></button>
				<div class="help-block">允许上传的图标文件格式为jpg, jpeg, gif, png</div>
			</div>
		</div>

		<input type="hidden" name="groupId" value="${category.groupId!}">
		<input type="hidden" name="parentId" value="${category.parentId!}">
		<input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
	</form>
</div>
<script type="text/javascript"> app.load('category/save-modal') </script>
</#macro>

<#macro blockFooter>
	<#if category.id??>
		<button type="button" class="btn btn-default pull-left delete-category" data-url="${ctx}/admin/category/${category.id}/delete" tabindex="5"><i class="glyphicon glyphicon-trash"></i> 删除</button>
	</#if>
  	<button type="button" class="btn btn-link" data-dismiss="modal" tabindex="6">取消</button>
	<button id="category-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#category-form" tabindex="4">保存</button>
</#macro>