<#assign submenu = 'user_center'/>
<#assign script_controller = 'setting/user-center'/>

<@block_title '管理员帐号同步'/>

<#include '/admin/system/user-set-layout.ftl'/>

<#macro blockMainContent>

  <#local user_center_name = 'Ucenter' />
  <#if mode! == 'phpwind'><#local user_center_name = 'WindID' /></#if>

  <div class="page-header">
    <h1>管理员帐号同步</h1>
</div>

<#if bind??>

<@web_macro.flash_messages />

<form class="form-horizontal with-discuz" method="post" novalidate id="sync-discuz-form">
  <div class="alert alert-info">
    <p><strong>注意事项：</strong></p>
    <p>同步管理帐号，是将用户中心{{ user_center_name }}的管理员帐号的用户名、Email、密码，覆盖掉EduoSoho中您<strong>当前登录</strong>的的管理帐号。<br>每个帐号只能同步一次。</p>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label"><label>{{ user_center_name }}管理员用户名</label></div>
    <div class="controls col-md-8">
      <input class="form-control" name="nickname">
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label"><label>{{ user_center_name }}管理员密码</label></div>
    <div class="controls col-md-8">
      <input type="password" class="form-control" name="password">
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label"></div>
    <div class="controls col-md-8">
      <button type="submit" class="btn btn-primary">同步</button>
      <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
    </div>
  </div>

</form>

<#else>
  <div class="alert alert-danger">该管理员帐号已同步，无需再同步。</div>

</#if>

</#macro>