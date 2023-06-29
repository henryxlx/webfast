<#assign side_nav = 'avatar'/>
<#assign script_controller = 'settings/avatar-crop'/>

<@block_title '头像'/>

<#include '/settings/layout.ftl'/>

<#macro blockMain>

  <div class="panel panel-default panel-col">
    <div class="panel-heading">
      头像设置
    </div>

    <div class="panel-body">
    <form id="avatar-crop-form" method="post" enctype="multipart/form-data">
      <@web_macro.flash_messages />

      <div class="form-group clearfix">
        <div class="col-md-offset-2 col-md-8 controls">
          <img src="${file_url(pictureUrl)}" id="avatar-crop" width="${scaledSize.width}" height="${scaledSize.height}" data-natural-width="${naturalSize.width}" data-natural-height="${naturalSize.height}" />
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

