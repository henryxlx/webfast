<#assign submenu = 'user_avatar'/>

<#include '/admin/system/user-set-layout.ftl'/>
<#macro blockTitle>注册设置 - 用户默认头像设置 - ${blockTitleParent}</#macro>

<#macro blockMainContent>
	<@web_macro.flash_messages />

	<div class="page-header"><h1>用户默认头像设置</h1></div>

	<fieldset>
		<legend>用户默认头像</legend>
		<div class="form-group">
			<div class="col-md-3 control-label"></div>
			<div class="controls col-md-8 radios" id="default-avatar" >
				<@radios 'avatar' {'1':'自定义默认头像', '0':'系统默认头像'} 'defaultSetting.defaultAvatar' />
				<p>网校内未上传头像的用户，都会显示该默认头像。推荐尺寸（120*120）</p>
				<#include '/admin/system/user-avatar-default.ftl'/>
			</div>
		</div>
	</fieldset>

</#macro>