<#if setting('default.defaultAvatar')??>
<div class="form-group" id="avatar-class">
    <div class="col-md-8 control-label"><b>自定义默认头像</b></div>
    <form id="avatar-form" method="post" action="${ctx}/setting/default/avatar" enctype="multipart/form-data">
        <div class="controls col-md-8 controls">
            <img src="${system_default_path('avatar', true)}">
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label"></div>
            <div class="controls col-md-8 controls">
                <input id="avatar-field" type="file" name="picture" accept="image/gif,image/jpeg,image/png">
                <p class="help-block">你可以上传JPG、GIF或PNG格式的文件。</p>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label"></div>
            <div class="controls col-md-8 controls">
                <button type="submit" class="btn btn-default btn-sm">上传</button>
            </div>
        </div>
        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
    </form>
</div>

<div class="form-group" id="system-avatar-class" style="display:none;">
    <div class="col-md-8 control-label"><b>系统默认头像</b></div>
    <div class="controls col-md-8 controls">
        <img src="${system_default_path('avatar')}">
    </div>
</div>

<#else>

<div class="form-group" id="avatar-class" style="display:none;">
    <div class="col-md-8 control-label"><b>自定义默认头像</b></div>
    <form id="avatar-form" method="post" action="${ctx}/setting/default/avatar" enctype="multipart/form-data">
        <div class="controls col-md-8 controls">
            <img src="${system_default_path('avatar', true)}">
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label"></div>
            <div class="controls col-md-8 controls">
                <input id="avatar-field" type="file" name="picture" accept="image/gif,image/jpeg,image/png">
                <p class="help-block">你可以上传JPG、GIF或PNG格式的文件。</p>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-2 control-label"></div>
            <div class="controls col-md-8 controls">
                <button type="submit" class="btn btn-default btn-sm">上传</button>
            </div>
        </div>
        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
    </form>
</div>

<div class="form-group" id="system-avatar-class">
    <div class="col-md-8 control-label"><b>系统默认头像</b></div>
    <div class="controls col-md-8 controls">
        <img src="${system_default_path('avatar')}">
    </div>
</div>

</#if>