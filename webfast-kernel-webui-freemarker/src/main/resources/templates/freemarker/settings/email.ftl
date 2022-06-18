<#assign side_nav = 'email'/>
<#assign script_controller = 'settings/email'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>邮箱设置 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <#local class='panel-col'/>
    <#include '/bootstrap/panel.ftl'/>
</#macro>

<#macro blockHeading>邮箱设置</#macro>
<#macro blockPanelBody>
<#if (mailer.enabled)?? && mailer.enabled == '1'>
<form id="setting-email-form" class="form-horizontal" method="post" >
    <@web_macro.flash_messages/>

    <div class="form-group">
        <div class="col-md-2 control-label"><label>当前登录邮箱</label></div>
        <div class="col-md-8 controls">
            <span class="control-text">
              ${(appUser.email)!}
              <#if (appUser.emailVerified)?? && appUser.emailVerified gt 0>
                <span class="text-success">(已验证)</span>
              <#else>
                <p class="help-block mtm">
                  邮箱地址尚未验证，
                  <a id="send-verify-email" class="btn btn-info btn-sm" href="javascript:;" data-url="${ctx}/settings/emailVerify">去验证</a>
                </p>
              </#if>
            </span>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-2 control-label"><label for="form_password">网站登录密码</label></div>
        <div class="col-md-8 controls">
            <input type="password" id="form_password" name="password" class="form-control" value="">
            <p class="help-block">设置新的登录邮箱，需要校验当前的网站登录密码</p>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-2 control-label"><label for="form_email">新登录邮箱</label></div>
        <div class="col-md-8 controls">
            <input type="text" id="form_email" name="email" class="form-control" value="">
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-2 control-label"></div>
        <div class="col-md-8 controls">
            <#-- {{ form_rest(form) }}-->
            <button id="email-save-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>
<#else>
<div class="alert alert-danger">管理员尚未设置邮件服务器，不能更改邮箱地址。</div>
</#if>
</#macro>