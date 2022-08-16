<#assign submenu = 'user_avatar'/>
<#assign script_controller = 'setting/default-avatar-crop' />

<#include '/admin/system/user-set-layout.ftl'/>
<#macro blockTitle>用户默认头像设置 - 用户设置 - ${blockTitleParent}</#macro>

<#macro blockMainContent>

<div class="panel panel-default panel-col">
  <div class="panel-heading">
    自定义默认头像
  </div>

  <div class="panel-body">
    <form id="default-avatar-crop-form" method="post" action="${ctx}/admin/setting/user-avatar-crop" enctype="multipart/form-data">
      <@web_macro.flash_messages />

      <div class="form-group clearfix">
        <div class="col-md-offset-2 col-md-8 controls">
          <img src="${ctx}${pictureUrl}" id="default-avatar-crop" width="${scaledSize.width}" height="${scaledSize.height}" data-natural-width="${naturalSize.width}" data-natural-height="${naturalSize.height}" />
          <div class="help-block">提示：请选择图片裁剪区域。</div>
        </div>
      </div>

      <div class="form-group clearfix">
        <div class="col-md-offset-2 col-md-8 controls">
          <input type="hidden" name="x">
          <input type="hidden" name="y">
          <input type="hidden" name="width">
          <input type="hidden" name="height">
          <button class="btn btn-fat btn-primary" id="upload-picture-btn" type="submit">保存</button>
          <a href="javascript:;" class="go-back btn btn-link">重新选择图片</a>
        </div>
      </div>

      <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

    </form>
  </div>
</div>

</#macro>