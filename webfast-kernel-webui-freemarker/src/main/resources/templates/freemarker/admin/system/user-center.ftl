<#assign submenu = 'user_center'/>
<#assign script_controller = 'setting/user-center'/>

<@block_title '用户中心设置'/>

<#include '/admin/system/user-set-layout.ftl'/>

<#macro blockMainContent>

    <div class="page-header">
        <#if (settingVo.mode)! !='default'><a href="${ctx}/admin/setting/admin-sync" class="pull-right btn btn-link">
                管理员帐号同步</a></#if>
        <h1>用户中心设置</h1>
    </div>

<@web_macro.flash_messages />

<form class="form-horizontal" method="post" novalidate>
  <div class="row form-group">
    <div class="col-md-3 control-label">
      <label >用户中心</label>
    </div>
    <div class="controls col-md-8 radios mode-radios">
      <@radios 'mode' {'default':'无', 'discuz':'Discuz (Ucenter)', 'phpwind':'phpwind (WindID)'} 'setting.mode'/>
      <div class="help-block">支持phpwind 9系列，Discuz X2.5/X3.0/X3.1的用户中心</div>
    </div>
  </div>

  <div class="row form-group with-discuz" style="display:none;">
    <div class="col-md-3 control-label"><label>Ucenter配置</label></div>
    <div class="controls col-md-8">
      <textarea class="form-control" name="discuz_config" rows="10">${discuzConfig!}</textarea>
      <div class="help-block">从Ucenter复制配置，粘帖到此处。注意：请不要去除头部的 &lt;?php。 <a href="http://demo.edusoho.com/course/125" target="_blank">如何配置？</a></div>
    </div>
  </div>

  <div class="row form-group with-phpwind" style="display:none;">
    <div class="col-md-3 control-label"><label>WindId配置</label></div>
    <div class="controls col-md-8">
      <textarea class="form-control" name="phpwind_config" rows="10">${phpwindConfig!}</textarea>
      <div class="help-block">如使用phpwind (WindID)的用户中心，请填写此项配置。<a href="http://demo.edusoho.com/course/125" target="_blank">如何配置？</a></div>
    </div>
  </div>

    <div class="form-group with-phpwind with-discuz">
    <div class="col-md-3 control-label">
      <label for="email_filter">过滤邮箱地址清单</label>
    </div>
    <div class="controls col-md-8">
      <textarea id="email_filter" name="email_filter" class="form-control" rows="5">${(settingVo.emailFilter)!}</textarea>
      <div class="help-block">
<#--        {% verbatim %}-->
        <div>每行输入一个邮箱地址。名单中的邮箱地址如果出现在第三方登录的过程中，则会给该用户随机生成一个邮箱</div>
<#--        {% endverbatim %}-->
      </div>
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-md-3 control-label">
      <label>用户修改昵称</label>
    </div>
    <div class="controls col-md-8 radios">
      <@radios 'nickname_enabled' {'1':'开启', '0':'关闭'} 'setting.nickname_enabled' />
    </div>
  </div>

  <div class="form-group">
    <div class="col-md-3 control-label">
      <label>头像设置提醒</label>
    </div>
    <div class="controls col-md-8 radios">
      <@radios 'avatar_alert' {'none':'从不', 'in_user_center':'每次进入"我的课程"时', 'when_join_course':'加入免费课程时强制要求'} 'setting.avatar_alert' />
    </div>
  </div>



  <div class="row form-group">
    <div class="col-md-3 control-label"></div>
    <div class="controls col-md-8">
      <button type="submit" class="btn btn-primary">提交</button>
    </div>
  </div>

  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

</#macro>