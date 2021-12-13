<#assign submenu = 'default'/>
<#assign script_controller = 'setting/default'/>

<#include '/admin/system/operation-layout.ftl'/>

<#macro blockTitle>系统默认设置 - 全局设置 - ${blockTitleParent}</#macro>

<#macro blockMainContent>
<meta http-equiv="pragma" content="no-cache" />
<@web_macro.flash_messages />

<div class="page-header"><h1>系统默认设置</h1></div>

<fieldset>
    <legend>用户默认头像</legend>
    <div class="form-group">
        <div class="col-md-3 control-label"></div>
        <div class="controls col-md-8 radios" id="default-avatar" >
            <@radios 'avatar' {'1':'自定义默认头像', '0':'系统默认头像'} 'defaultSetting.defaultAvatar' />
            <p>网校内未上传头像的用户，都会显示该默认头像。推荐尺寸（120*120）</p>
            <#include '/admin/system/default-avatar.ftl'/>
        </div>
    </div>
</fieldset>

<#if setting('copyright.owned')??>
<fieldset>
    <legend>课程默认图片</legend>
    <div class="form-group">
        <div class="col-md-3 control-label"></div>
        <div class="controls col-md-8 radios" id="defaultCoursePicture">
            <@radios 'coursePicture' {'1':'自定义默认课程图片', '0':'系统默认课程图片'}, 'defaultSetting.defaultCoursePicture' />
            <p>网校内未上传图片的课程，都会显示该图片。推荐尺寸（360*180）</p>
            {% include '/admin/system/default-course-picture.ftl' %}
        </div>
    </div>
</fieldset>
</#if>

<div class="page-header" style="display:none;"><h1>分享内容</h1></div>
<legend></legend>
<form class="form-horizontal" id="cloud-setting-form" method="post" novalidate>
    <input type="hidden" name="defaultAvatar" value="{{ defaultSetting.defaultAvatar }}">
    <input type="hidden" name="defaultCoursePicture" value="{{ defaultSetting.defaultCoursePicture }}">

    <fieldset>
        <legend>名称设置</legend>

        <div class="row form-group">
            <div class="col-md-2 col-md-offset-2 control-label">
                <label for="user_name">用户名称</label>
            </div>
            <div class="controls col-md-4">
                <input  type="text" id="user_name" name="user_name" class="form-control" value="${(defaultSetting.userName)!'学员'}">
            </div>
        </div>

        <div class="row form-group">
            <div class="col-md-2 col-md-offset-2 control-label">
                <label for="chapter_name">课程章名称</label>
            </div>
            <div class="controls col-md-4">
                <input  type="text" id="chapter_name" name="chapter_name" class="form-control" value="${(defaultSetting.chapterName)!'章'}">
            </div>
        </div>

        <div class="row form-group">
            <div class="col-md-2 col-md-offset-2 control-label">
                <label for="part_name">课程节名称</label>
            </div>
            <div class="controls col-md-4">
                <input  type="text" id="part_name" name="part_name" class="form-control" value="${(defaultSetting.partName)!'节'}">
            </div>
        </div>

        <div class="alert alert-info deduction text-center">
            <p><strong>
                    注：修改网校字段后，站内相关字段名称都会相应修改为已保存的名称
                </strong></p>
        </div>

    </fieldset>

    <div class="form-group">
        <div class="controls col-md-offset-3 col-md-8">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
    <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
</form>

</#macro>