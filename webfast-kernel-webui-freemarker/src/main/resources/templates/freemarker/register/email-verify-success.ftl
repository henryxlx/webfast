<#assign script_controller = 'auth/email-verify'/>

<@block_title '邮箱验证成功'/>

<#include '/layout.ftl'>

<#macro blockContent>
  <div class="row">
    <div class="col-md-6 col-md-offset-3 ptl">
      <div class="panel panel-default panel-page">
        <div class="panel-heading"><h2>邮箱验证成功</h2></div>
        <input type="hidden" name="verifyUrl" value="${ctx}/register/email/verify/${(token.token)!}"/>

        <p>正在跳转到首页...<a id="jump-btn" href="${ctx}" class="mll"> &raquo; 点此直接进入</a></p>

    </div><!-- /panel -->

  </div>

</div>
</#macro>