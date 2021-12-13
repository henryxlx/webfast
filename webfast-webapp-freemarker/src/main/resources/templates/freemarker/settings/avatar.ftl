<#assign side_nav = 'avatar'/>
<#assign script_controller = 'settings/avatar'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>头像 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <#local class='panel-col'/>
    <#include '/bootstrap/panel.ftl'/>
</#macro>

<#macro blockHeading>头像</#macro>
<#macro blockPanelBody>
<#if fromCourse??>
<div class="alert alert-info">请设置自定义头像。</div>
</#if>

<form id="settings-avatar-form" class="form-horizontal" method="post" enctype="multipart/form-data">

    <@web_macro.flash_messages/>

    <div class="form-group">
        <div class="col-md-2 control-label"><b>当前头像</b></div>
        <div class="controls col-md-8 controls">
            <img src="${default_path('avatar', (user.largeAvatar)!, '')}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-2 control-label">
<#--            <@form_label form.avatar '新头像'/>-->
            <label for="form_avatar" class="required">新头像</label>
        </div>
        <div class="controls col-md-8 controls">
<#--            <@form_widget form.avatar {'attr' : { 'accept': 'image/gif,image/jpeg,image/png'}}/>-->
            <input type="file" id="form_avatar" name="avatar" required="required" accept="image/gif,image/jpeg,image/png"  />
            <p class="help-block">你可以上传JPG、GIF或PNG格式的文件，文件大小不能超过<strong>${upload_max_filesize()!'2.0MB'}</strong>。</p>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-2 control-label"></div>
        <div class="controls col-md-8 controls">
<#--            <@form_rest(form)/>-->
            <input type="hidden" id="form_token" name="formToken" value="e240727a365956e36cf595a0dc8c260e397a4908" />
            <button type="submit" class="btn btn-primary">上传</button>
            <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
        </div>
    </div>

    <#if partnerAvatar??>
    <div class="form-group">
        <div class="col-md-2 control-label"><b>论坛头像</b></div>
        <div class="controls col-md-8 controls">
            <img src="{{ partnerAvatar }}" class="mrm">
            <button class="btn btn-default use-partner-avatar" type="button" data-url="${ctx}/settings/avatar/fetchPartner" data-goto="${ctx}/settings/avatar">使用该头像</button>
        </div>
    </div>
    </#if>

</form>

</#macro>