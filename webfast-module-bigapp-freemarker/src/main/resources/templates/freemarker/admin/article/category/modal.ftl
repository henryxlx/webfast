<#assign modal_class = 'modal-lg'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle><#if category.id gt 0>编辑栏目<#else>添加栏目</#if></#macro>

<#macro blockBody>
	<form id="category-form" class="form-horizontal" action="<#if category.id gt 0>${ctx}/admin/article/category/${category.id}/edit<#else>${ctx}/admin/article/category/create</#if>" method="post">
		<div class="form-group">
			<label class="col-md-2 control-label" for="category-name-field">栏目名称</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-name-field" type="text" name="name" value="${category.name!}" tabindex="1">
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-code-field">栏目编码</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-code-field" type="text" name="code" value="${category.code!}" data-url="${ctx}/admin/article/category/checkcode?exclude=${category.code}" tabindex="2">
				<div class="help-block">生成列表、资讯时使用，例如news、bbs等</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-parentId-field">父栏目</label>
			<div class="col-md-8 controls">
				<select class="form-control" id="category-parentId-field" type="text" name="parentId" tabindex="2" data-url="${ctx}/admin/article/category/checkparentid?currentId=${category.id}">
					<option value=0>选择栏目</option>
					<#list categoryTree! as tree>
					<option value="${tree.id}" <#if tree.id == category.parentId>selected</#if>><#list 0..(tree.depth-1)*2 as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>└─ ${tree.name}</option>
					</#list>
				</select>
			</div>		
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-weight-field">显示顺序号</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-weight-field" type="text" name="weight" value="${category.weight!}" tabindex="3">
				<div class="help-block">自然数，数字越小，位置越前</div>
			</div>
		</div>

		<div style="display:none;" class="form-group">
			<label class="col-md-2 control-label" for="category-publishArticle-field">允许发布资讯</label>
			<div class="controls radios col-md-8"><label>
					<input  id="category-publishArticle-field" type="radio" name="publishArticle" tabindex="0" value=1 <#if category.publishArticle ==1>checked</#if>>是</label>
				<label>
					<input  id="category-publishArticle-field" type="radio" name="publishArticle" tabindex="0" value=0 <#if category.publishArticle !=1>checked</#if>>否
				</label>	
				
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-seoTitle-field">SEO标题</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-seoTitle-field" type="text" name="seoTitle" value="${category.seoTitle!}" tabindex="4">
				<div class="help-block"></div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-seoKeyword-field">SEO关键字</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-seoKeyword-field" type="text" name="seoKeyword" value="${category.seoKeyword!}" tabindex="4">
				<div class="help-block"></div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-seoDesc-field">SEO描述</label>
			<div class="col-md-8 controls">
				<input class="form-control" id="category-seoDesc-field" type="text" name="seoDesc" value="${category.seoDesc!}" tabindex="4">
				<div class="help-block"></div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-md-2 control-label" for="category-published-field">启用</label>
			<div class="controls radios col-md-8">
				<label><input id="category-published-field" type="radio" name="published" tabindex="0" value=1 <#if category.published ==1>checked</#if>>是</label>
				<label><input id="category-published-field" type="radio" name="published" tabindex="0" value=0 <#if category.published !=1>checked</#if>>否</label>
				<div class="help-block">建议等栏目内容整理编辑完毕后再启用</div>
			</div>
		</div>
		<input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
	</form>
	
	<script type="text/javascript"> app.load('article/category/save-modal') </script>
</#macro>

<#macro blockFooter>
	<#if category.id gt 0>
		<button type="button" class="btn btn-default pull-left delete-category" data-url="${ctx}/admin/article/category/${category.id}/delete" tabindex="5"><i class="glyphicon glyphicon-trash"></i> 删除</button>
	<#else>

	</#if>
  	<button type="button" class="btn btn-link" data-dismiss="modal" tabindex="6">取消</button>
	<button id="category-save-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary" data-toggle="form-submit" data-target="#category-form" tabindex="4">保存</button>
</#macro>