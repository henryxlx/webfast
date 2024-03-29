<#assign submenu = 'mailer'/>
<#assign script_controller = 'setting/mailer'/>

<@block_title '电子邮件'/>

<#include '/admin/system/operation-layout.ftl'/>

<#macro blockMainContent>
    <div class="page-header"><h1>邮件服务器设置</h1></div>

    <@web_macro.flash_messages />

    <form class="form-horizontal" method="post" id="mailer-form" novalidate>
        <div class="row form-group">
    <div class="col-md-3 control-label">
      <label >邮件发送</label>
    </div>
    <div class="controls col-md-8 radios">
      <@radios 'enabled' {'1':'开启', '0':'关闭'} '' + mailer.enabled! />
    </div>
  </div>

  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="host">SMTP服务器地址</label>
    </div>
    <div class="controls col-md-8">
      <input type="text" id="host" name="host" class="form-control" value="${(mailer.host)!}">
      <p class="help-block">例：smtp.yourmail.com</p>
    </div>
  </div>
  <p></p>
  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="port">SMTP端口号</label>
    </div>
    <div class="controls col-md-8">
      <input type="text" id="port" name="port" class="form-control" value="${(mailer.port)!}">
      <p class="help-block">通常端口号为25</p>
    </div>
  </div>
 <p></p>
  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="username" >SMTP用户名</label>
    </div>
    <div class="controls col-md-8">
      <input type="text" id="username" name="username" class="form-control" value="${(mailer.username)!}">
      <p class="help-block">正确格式应为abc@mail.com</p>
    </div>
  </div>
 <p></p>

  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="password" >SMTP密码</label>
    </div>
    <div class="controls col-md-8">
      <input type="password" id="password" name="password" class="form-control" value="${(mailer.password)!}">
    </div>
  </div>
 <p></p>

 <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="from" >发信人地址</label>
    </div>
    <div class="controls col-md-8">
      <input type="text" id="from" name="from" class="form-control" value="${(mailer.from)!}">
      <p class="help-block">正确格式应为abc@mail.com</p>
    </div>
  </div>
 <p></p>

 <div class="row form-group">
    <div class="col-md-3 control-label">
      <label for="name" >发信人名称</label>
    </div>
    <div class="controls col-md-8">
      <input type="text" id="name" name="name" class="form-control" value="${(mailer.name)!}">
    </div>
  </div>
 <p></p>
  <div class="row form-group">
    <div class="col-md-3 control-label"></div>
    <div class="controls col-md-8">
      <button type="submit" class="btn btn-primary">提交</button>
    </div>
  </div>

  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

</#macro>