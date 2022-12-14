<#assign modal_class = 'modal-lg'/>
<#assign hideFooter = true/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>${type.name}</#macro>

<#macro blockBody>
<form class="two-col-form" id="content-form" method="post" enctype="multipart/form-data"
    <#if content??>
		action="${ctx}/admin/content/${content.id}/edit"
	<#else>
		action="${ctx}/admin/content/create?type=${type.alias}"
	</#if>
	>
	<div class="row">
		<div class="col-md-8 two-col-main">
			<#if type.basicFields?seq_contains('title')>
				<div class="form-group">
				  <label for="content-title-field" class="control-label">标题</label>
				  <div class="controls">
				  	<input class="form-control" id="content-title-field" type="text" name="title" value="${(content.title)!}">
				  </div>
				</div>
			</#if>

			<div class="form-group">
				<label for="editor-field" class="control-label">编辑器类型</label>
				<div class="controls radios">
	              	<label><input type="radio" name="editor" value="richeditor" 
	              		<#if !content??>
	              		 checked="checked" 
	              		<#elseif content.editor == 'richeditor'>
	              		 checked="checked"
						</#if>
	              		>可视化编辑器</label>
	             	<label><input type="radio" name="editor" value="none"
	             		<#if content?? && content.editor == 'none'> checked="checked" </#if>
	             		>HTML编辑器</label>
	            </div>
	        </div>

			<#if type.basicFields?seq_contains('body')>
				<label for="content-body-field" class="control-label">正文</label>

				<div class="form-group" 
				<#if content?? && content.editor == 'none'>
					style="display:none"
				</#if>
				>
					<div class="controls">
						<textarea class="form-control" id="richeditor-body-field" rows="16" name="richeditor-body"
						  data-image-upload-url="{{ path('editor_upload', {token:upload_token('default')}) }}"
						  data-flash-upload-url="{{ path('editor_upload', {token:upload_token('default', 'flash')}) }}"
						>${(content.body)!}</textarea>
					</div>
				</div>

				<div class="form-group" 
				<#if !content??>
          		 style="display:none" 
          		<#elseif content.editor == 'richeditor'>
          		 style="display:none"
          		</#if>
				>
					<div class="controls">
						<textarea class="form-control" id="noneeditor-body-field" rows="16" name="noneeditor-body">${(content.body)!}</textarea>
					</div>
				</div> 
			</#if>

			<#if (type.extendedFields)??>
			  {% include 'TopxiaAdminBundle:Content:' ~ type.alias ~ '-extended-fields.html.twig' %}
			</#if>
		</div>
		<div class="col-md-4 two-col-side">
			<#if type.basicFields?seq_contains('alias')>
				<div class="panel panel-default">
				  <div class="panel-heading">URL路径</div>
				  <div class="panel-body">
					  <div class="form-group">
					  	<div class="controls">
							  <input class="form-control" type="text" name="alias" value="${(content.alias)!}" data-url="${ctx}/admin/content/alias/check?that=${(content.alias)!}">
					  	</div>
					  </div>
				  </div>
				</div>
			</#if>

			<#if type.basicFields?seq_contains('categoryId')>
				<div class="panel">
				  <div class="panel-heading">分类</div>
				  <select name="categoryId">
				  	{{ select_options(category_choices('default'), field_value(content, 'categoryId'), '请选择分类') }}
				  </select>
				</div>
			</#if>

			<#if type.basicFields?seq_contains('tagIds')>
				<div class="panel">
				  <div class="panel-heading">标签</div>
				  <div class="form-group">
				  	<div class="controls" style="width:100%;">
						  <input class="width-full" id="content-tags-field" type="text" name="tags" value="{{ field_value(content, 'tagIds')|tags_join }}">
				  	</div>
				  </div>
				</div>
			</#if>

			<#if type.basicFields?seq_contains('template')>
				<div class="panel panel-default" style="display:none">
				  <div class="panel-heading">模版</div>
				  <div class="panel-body">
					  {% set templates = {'default': '默认模版', 'customize': '自定义模版'} %}
					  {% set templates = {'default': '默认模版'} %}
					  <select class="form-control" name="template">
					  	{{ select_options(templates, field_value(content, 'template')) }}
					  </select>
				  </div>
				</div>
			</#if>

			<input type="hidden" name="publishedTime" value="{{ field_value(content, 'publishedTime', app.request.server.get('REQUEST_TIME'))|date('Y-m-d H:i:s') }}">

			<#--
			<#if type.basicFields?seq_contains('picture')>
				<div class="panel panel-default" id="picture-panel">
					<div class="panel-heading">附图</div>
					<div class="panel-body">
					  <p data-role="picture-container">
					  	{% if content.picture|default(null) %}
					  	  <a href="{{ file_path(content.picture) }}" target="_blank">
						  	  <img src="{{ file_path(content.picture) }}" class="img-responsive">
					  	  </a>
					  	{% endif %}
					  </p>
					  <p> <input type="file" name="picture"> </p>
					</div>
				</div>
			</#if>

			<div class="panel panel-default">
				<div class="panel-heading">设置</div>
				<div class="panel-body">
					<div>
						<label>发布时间</label><input type="text" name="publishedTime" value="{{ field_value(content, 'publishedTime', app.request.server.get('REQUEST_TIME'))|date('Y-m-d H:i:s') }}">
						<div class="help-block">内容条目默认按发布时间的倒序显示</div>
					</div>

					<div class="checkbox">
						<label><input type="checkbox" name="promoted" value="1" {% if field_value(content, 'promoted', 1) %} checked="checked" {% endif %}> 在列：<span class="text-muted">在列表中显示</span></label>
					</div>

					<div class="checkbox">
						<label><input type="checkbox" name="sticky" value="1" {% if field_value(content, 'sticky') %} checked="checked" {% endif %}> 置顶：<span class="text-muted">内容条目显示在列表顶部</span></label>
					</div>
					<div class="checkbox">
						<label><input type="checkbox" name="featured" value="1" {% if field_value(content, 'featured') %} checked="checked" {% endif %}> 头条：<span class="text-muted">在头条区域显示</span></label>
					</div>
				</div>
			</div>
			-->
			

		</div>
	</div>
	<input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

<script> app.load('content/content-modal') </script>

</#macro>

<#macro blockFooter>

    <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
    <button id="content-save-btn" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#content-form" data-loading-text="正在保存...">保存</button>
</#macro>
