<#assign side_nav = 'profile'/>
<#assign script_controller = 'settings/approval'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>实名认证 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <#local modal_class = 'panel-col'/>
    <#include '/bootstrap/panel.ftl'>
</#macro>

<#macro blockHeading>实名认证</#macro>

<#macro blockPanelBody>

<#assign approvalStatusList = ['approved', 'unapprove', 'approve_fail']/>
<#if approvalStatusList?seq_contains(appUser.approvalStatus)>
<form id="approval-form" class="form-horizontal" method="post" enctype="multipart/form-data">

	<@web_macro.flash_messages />

	<div class="form-group">
		<div class="col-md-2 control-label">
			<label for="truename">真实姓名</label>
		</div>
		<div class="col-md-8 controls">
			<input type="text" id="truename" name="truename" class="form-control" value="">
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-2 control-label">
			<label for="idcard">身份证号</label>
		</div>
		<div class="col-md-8 controls">
			<input type="text" id="idcard" name="idcard" class="form-control" value="">
		</div>
	</div>


	<div class="form-group">

		<div class="col-md-2 control-label">
			<label>照片要求</label>
		</div>
		<div class="controls col-md-8 controls">
			<p class="text-warning">证件必须是清晰彩色原件电子版，可以是扫描或者数码相机拍摄照片。<br />图片格式支持jpg、gif、png，每张图片文件大小不能超过2M。</p>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-2 control-label">
			<label for="faceImg">身份证正面照</label>
		</div>
		<div class="controls col-md-8 controls">
			<input type="file" id="faceImg" name="faceImg" accept="image/bmp,image/jpeg,image/png">
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-2 control-label">
			<label for="backImg">身份证反面照</label>
		</div>
		<div class="controls col-md-8 controls">
			<input type="file" id="backImg" name="backImg" accept="image/bmp,image/jpeg,image/png">
		</div>
	</div>

	<div class="row">
		<div class="col-md-7 col-md-offset-2">
			<button type="submit" class="btn btn-primary">提交</button>
			<a class="btn btn-link" href="${ctx}/settings">返回</a>
		</div>
	</div>

	<input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

</#if>

</#macro>