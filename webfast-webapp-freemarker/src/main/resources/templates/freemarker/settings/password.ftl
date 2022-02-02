<#assign side_nav = 'security'/>
<#assign script_controller = 'settings/password'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>密码修改 - 安全设置 - ${blockTitleParent}</#macro>

<#macro blockMain>

  <div class="panel panel-default panel-col">
    <div class="panel-heading">安全设置</div>
    <div class="panel-body">

      <ul class="breadcrumb">
        <li><a href="${ctx}/settings/security">安全设置</a></li>
        <li class="active">登陆密码修改</li>
      </ul>  


      <#if appUser.password == ''>
        <div class="alert alert-warning">
          <p>当前帐号从第三方帐号直接登录时创建，尚未设置密码。</p>
          <p>为了帐号的安全，请通过邮箱找回密码的方式，重设密码！</p>
          <p><a href="{{ path('password_reset') }}" class="btn btn-primary">现在就去重设密码！</a></p>
        </div>
      </#if>

      <form id="settings-password-form" class="form-horizontal" method="post" <#if appUser.password == ''> style="display:none;"</#if>>

        <@web_macro.flash_messages />
        
        <div class="form-group">
          <div class="col-md-2 control-label"><label for="currentPassword">当前密码</label></div>
          <div class="controls col-md-8 controls">
            <input type="password" id="currentPassword" name="currentPassword" class="form-control" value="">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-2 control-label"><label for="form_newPassword">新密码</label></div>
          <div class="controls col-md-8 controls">
            <input type="password" id="form_newPassword" name="newPassword" class="form-control" value="">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-2 control-label"><label for="confirmPassword">确认密码</label></div>
          <div class="controls col-md-8 controls">
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" value="">
          </div>
        </div>

        <div class="form-group">
          <div class="col-md-2 control-label"></div>
          <div class="controls col-md-8 controls">
            <input type="hidden" id="form_token" name="formToken" value="e240727a365956e36cf595a0dc8c260e397a4908" />
            <button id="password-save-btn" data-submiting-text="正在保存" type="submit" class="btn btn-primary">提交</button>
          </div>
        </div>

        <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">

      </form>

    </div>
  </div>

</#macro>