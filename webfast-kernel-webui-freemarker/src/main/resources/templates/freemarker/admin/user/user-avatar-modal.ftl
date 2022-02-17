<#assign modalSize = 'modal-lg'/>

<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>设置用户头像</#macro>

<#macro blockBody>

  <form id="user-avatar-form" class="form-horizontal" method="post" enctype="multipart/form-data" action="${ctx}/admin/user/${user.id}/avatar">

    <@web_macro.flash_messages />

    <div class="form-group">
      <div class="col-md-2 control-label"><b>当前头像</b></div>
      <div class="controls col-md-8 controls">
      	<img src="${default_path('avatar', user.largeAvatar, '')}">
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label">
        <label class="required" for="form_avatar">新头像</label>
      </div>
      <div class="controls col-md-8 controls">
        <input name="form[avatar]" id="form_avatar" required="required" type="file"
               accept="image/gif,image/jpeg,image/png" data-widget-cid="widget-8"/>
        <p class="help-block">你可以上传JPG、GIF或PNG格式的文件，文件大小不能超过<strong>${upload_max_filesize()}</strong>。</p>
      </div>
    </div>

    <div class="form-group">
      <div class="col-md-2 control-label"></div>
      <div class="controls col-md-8 controls">
        <a id="avatar-upload-btn" data-url="${ctx}/admin/user/${user.id}/avatar" class="btn btn-primary">上传</a>
        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
      </div>
    </div>

    <#if partnerAvatar??>
      <div class="form-group">
        <div class="col-md-2 control-label"><b>论坛头像</b></div>
        <div class="controls col-md-8 controls">
          <img src="{{ partnerAvatar }}" class="mrm">
          <button class="btn btn-default use-partner-avatar" type="button" data-url="{{ path('settings_avatar_fetch_partner') }}" data-goto="{{ path('settings_avatar') }}">使用该头像</button>
        </div>
      </div>
    </#if>

  </form>

</#macro>

<#macro blockFooter>
  <button type="button" class="btn btn-link pull-right" data-dismiss="modal">关闭</button>
  <script>app.load('user/avatar-modal')</script>
</#macro>