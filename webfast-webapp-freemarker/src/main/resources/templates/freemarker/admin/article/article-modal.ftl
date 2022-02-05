<#assign modal_size = 'xlarge' />
<#assign menu = 'article'/>
<#assign script_controller = 'article/article-modal'/>

<#include '/admin/content/layout.ftl'/>
<#macro blockTitle>资讯管理>><#if article??>编辑<#else>添加</#if>资讯 - ${blockTitleParent}</#macro>

<#macro blockMain>

<style>
  #article-form .popover {
  	max-width: 400px;
  	width: 400px;
  }	

</style>

<div class="page-header clearfix">
 	<h1 class="pull-left"><#if article??>编辑<#else>添加</#if>资讯</h1>
</div>

<form class="two-col-form" id="article-form" method="post" enctype="multipart/form-data"
	<#if article??>
		action="${ctx}/admin/article/${article.id}/edit"
	<#else>
		action="${ctx}/admin/article/create"
	</#if>
	>
	<div class="row">
		<div class="col-md-8">
			<div class="form-group">
			  <label for="article-title-field" class="control-label">资讯标题</label>
			  <div class="controls">
			  	<input class="form-control" id="article-title-field" type="text" name="title" value="${(article.title)!}">
			  </div>
			</div>
			
			<div class="form-group">
			  <label for="categoryId" class="control-label">所属栏目</label>
			   <div class="controls">
				  <select class="form-control" id="categoryId" type="text" name="categoryId" tabindex="2">
					  <#if article??><#else><option value="">-选择所属栏目-</option></#if>
					    <#list categoryTree! as tree>
							<option value=${tree.id} <#if tree.id == category.id>selected</#if>><#list 0..(tree.depth-1)*2 as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>└─ ${tree.name}</option>
						</#list>
		      </select>
			   </div>
			</div>

			<div class="form-group">
	      <label for="article-tagIds"　class="control-label">TAG标签</label>
			  <div class="controls">
				  	<input type="form-control" id="article-tags" name="tags" required="required" class="width-full select2-offscreen" tabindex="-1" value="{{ tagNames|default([])|join(',') }}" data-match-url="{{ path('tag_match') }}">
				  	<div class="help-block" style="display:none;"></div>
			  </div>
			</div>

			<div class="form-group">
				<label for="richeditor-body-field" class="control-label">正文</label>
				<div class="controls">
					<textarea class="form-control" id="richeditor-body-field" rows="16" name="body"
					  data-image-upload-url="{{ path('editor_upload', {token:upload_token('default')}) }}"
					  data-flash-upload-url="{{ path('editor_upload', {token:upload_token('default', 'flash')}) }}"
					 >${(article.body)!}</textarea>
				</div>
			</div>
		
		</div>
		<div class="col-md-4">

			<div class="panel panel-default">
				<div class="panel-heading">资讯属性</div>

				<div class="panel-body">
				  <label class="checkbox-inline">
						<input type="checkbox" name="sticky" value="1" {% if field_value(article, 'sticky') %} checked="checked" {% endif %}> 置顶
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="featured" value="1" {% if field_value(article, 'featured') %} checked="checked" {% endif %}> 头条  
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" name="promoted" value="1" {% if field_value(article, 'promoted') %} checked="checked" {% endif %}> 推荐  
					</label>
					<a class="glyphicon glyphicon-question-sign text-muted pull-right" id="article-property-tips" data-toggle="tooltip" data-placement="bottom" href="javascript:" title="">
					</a>
					<div id="article-property-tips-html" style="display:none;">

						<p>设为“<strong>置顶</strong>”后，该资讯将会显示在列表的最前面。</p>
						<p>设为“<strong>头条</strong>”后，如果正文含有图片，则该资讯的第一张图片将显示在资讯首页的头条图片区，图片尺寸建议统一为：720*400。</p>
						<p>设为“<strong>推荐</strong>”后，该资讯将会显示在页面右侧的推荐资讯中。</p>

					</div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">来源设置</div>
				<div class="panel-body">
				  <div class="form-group">
				    <label for="article-source-field">来源名称</label>
				    <div class="controls">
				      <input class="form-control" id="article-source-field" type="text" name="source" value="{{ field_value(article, 'source') }}">
				    </div>
				  </div>

				  <div class="form-group">
			      <label for="article-sourceUrl-field">来源地址</label>
				    <div class="controls">
				      <input class="form-control" id="article-sourceUrl-field" type="text" name="sourceUrl" value="{{ field_value(article, 'sourceUrl') }}">
				    </div>
				  </div>

				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">设置缩略图</div>
				<div class="panel-body">
					<div id="article-thumb-container">
					  <#if (article.thumb)??>
						  <img src='{{ file_url(article.thumb) }}'>
					  </#if>
				   </div>
				   <br>
					<a href="#modal" data-toggle="modal" data-url="{{path('admin_article_show_upload')}}" class="btn btn-default">上传</a>
					<a id="article_thumb_remove"  class="btn btn-default" data-url="{{ path('admin_article_thumb_remove',{id:article.id|default(0)}) }}" {% if not article.thumb|default(null) %}style="display:none;" {% endif %}>删除</a>
					<input type="hidden" name="thumb" value="{{article.thumb |default()}}" id="article-thumb">
					<input type="hidden" name="originalThumb" value="{{article.originalThumb |default()}}" id="article-originalThumb">
					<p class="help-block">请上传png, gif, jpg格式的图片文件。</p>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">发布时间</div>
				<div class="panel-body">
					<div class="form-group">
						<div class="controls">
							<input class="form-control" type="text" name="publishedTime" value=" {{ field_value(article, 'publishedTime', app.request.server.get('REQUEST_TIME'))|date('Y-m-d H:i:s') }}">
							<div class="help-block">内容条目默认按发布时间的倒序显示</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
	    <button id="article-operate-save" class="btn btn-primary" data-toggle="form-submit" data-loading-text="正在保存...">确定</button>
			<a class="btn btn-link" href="${ctx}/admin/article">返回</a>
		</div>		

	</div>

</form>

</#macro>
