<#assign submenu = 'login_bind'/>
<#assign script_controller = 'system/login_bind'/>

<@block_title '登录设置'/>

<#include '/admin/system/user-set-layout.ftl'/>

<#macro blockMainContent>
    <div class="page-header"><h1>登录设置</h1></div>

    <@web_macro.flash_messages />

    <form class="form-horizontal" method="post" id="login_bind-form" novalidate>

        <fieldset>
    <div class="form-group">
      <div class="col-md-3 control-label">
        <label >用户登录限制</label>
      </div>
      <div class="controls col-md-8 radios">
        <@radios 'login_limit', {'1':'开启', '0':'关闭'}, ''+loginConnect.login_limit />
      <p class="help-block">开启后同一帐号只能在一处登录</p>
      </div>
    </div>
  </fieldset>
<br>

  <fieldset>
    <div class="form-group">
      <div class="col-md-3 control-label">
        <label >第三方登录</label>
      </div>
      <div class="controls col-md-8 radios">
        <@radios 'enabled', {'1':'开启', '0':'关闭'}, ''+loginConnect.enabled />
      </div>
    </div>
  </fieldset>
<br>


<fieldset>
<div class="form-group">
      <div class="col-md-3 control-label">
          <label >用户登陆保护</label>
      </div>
      <div class="controls col-md-8 radios">
          <@radios 'temporary_lock_enabled' {'1':'开启', '0':'关闭'} ''+loginConnect.temporary_lock_enabled />
          <p class="help-block">开启后，登陆时用户连续多次输入错误密码时暂时封禁用户,此功能不影响admin手动永久封禁用户</p>
      </div>

      <div id="times_and_minutes" class="col-md-8 col-md-offset-3">
        <div class="row">
          <div class="col-md-4 lock-user-text-right">
            用户连续输入错误密码
          </div>
          <div class="controls col-md-2 form-group">
            <input type="text" id="temporary_lock_allowed_times" name="temporary_lock_allowed_times" class="form-control" value="${(loginConnect.temporary_lock_allowed_times)!}">
          </div>
          <div class="col-md-3 lock-user-text-left">
            次，将暂时封禁用户
          </div>
        </div>
        <br>
        <div class="row">
          <div class="col-md-4 lock-user-text-right">
            经过
          </div>
          <div class="controls col-md-2 form-group">
            <input type="text" id="temporary_lock_minutes" name="temporary_lock_minutes" class="form-control" value="${(loginConnect.temporary_lock_minutes)!}">
          </div>
          <div class="col-md-3 lock-user-text-left">
            分钟后，解锁用户
          </div>  
        </div>
      </div> 
  </div>
  </fieldset>

  <#list clients! as type, client>
    <fieldset data-role="oauth2-setting" data-type="${type}">
      <legend>${client.name}</legend>
      <div class="form-group">
        <div class="col-md-3 control-label">
            <label >${client.name}</label>
        </div>
        <div class="controls col-md-8 radios">
          <@radios type + '_enabled' {'1':'开启', '0':'关闭'}  "loginConnect[type + '_enabled']" />
          <#if client.apply_url??>
            <div class="help-block"><a href="${client.apply_url}" target="_blank">申请${client.name}</a></div>
          </#if>
        </div>
      </div>

      <div class="form-group">
        <div class="col-md-3 control-label">
          <label for="${type}_key" >${client.key_setting_label}</label>
        </div>
        <div class="controls col-md-8">
          <input type="text" id="${type}_key" name="${type}_key" class="form-control" value="${loginConnect[type + '_key']}">
        </div>
      </div>

      <div class="form-group">
        <div class="col-md-3 control-label">
          <label for="${type}_secret" >${(client.secret_setting_label)!}</label>
        </div>
        <div class="controls col-md-8">
          <input type="text" id="${type}_secret" name="${type}_secret" class="form-control" value="${(loginConnect[type + '_secret'])!}">
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-3 control-label">
          <label for="">登录时必须设置帐号和邮箱</label>
        </div>
          <div class="controls col-md-8 radios">    
            <@radios type + '_set_fill_account' {'1':'开启', '0':'关闭'} "loginConnect[type + '_set_fill_account']" />
            <p class="help-block">在${(client.name)!}官方审核通过您的接入申请以前，请不要开启此选项，否则可能审核将无法通过</p>
          </div>
      </div>
    </fieldset>
  </#list>

  <fieldset>
    <legend>登录接口验证代码</legend>
    <div class="form-group">
      <div class="col-md-3 control-label">
        <label for="verify_code" >验证代码</label>
      </div>
      <div class="controls col-md-8">
        <textarea id="verify_code" name="verify_code" class="form-control" rows="5">${(loginConnect.verify_code)!}</textarea>
        <div class="help-block">在申请QQ登录等接口时，会要求验证您的网站域名。请把相关验证代码粘到此处，然后提交保存。</div>
      </div>
    </div>
  </fieldset>

  <div class="form-group">
    <div class="controls col-md-offset-3 col-md-8">
      <button type="submit" class="btn btn-primary">提交</button>
    </div>
  </div>
  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

</#macro>