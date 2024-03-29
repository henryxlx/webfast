<#assign script_controller = 'auth/register-submited'/>

<@block_title '注册'/>

<#include '/layout.ftl'>

<#macro blockContent>
  <div class="row">
    <div class="col-md-6 col-md-offset-3 ptl">
      <div class="panel panel-default panel-page">
        <div class="panel-heading"><h2>注册还需一步</h2></div>

        <div id="email-sending" class="alert alert-info" style="display:none;">正在发送注册验证邮件，请稍等...</div>

      <div id="email-send-success">
        <p class="text-success">注册确认信已经发到你的邮箱<strong>${(user.email)!}</strong>，你需要点击邮件中的确认链接来完成邮箱验证，验证后才可以登录．</p>

        <p class="mvl">
          <a class="btn btn-primary" href="${emailLoginUrl!}" target="_blank">登录邮箱查收邮件</a>
        </p>

        <div class="alert">
          <h5>没有收到确认信怎么办？</h5>
          <ul>
            <li>检查一下上面Email地址是否正确，错了就重新<a class="text-muted" href="${ctx}/logout?goto=register">注册</a>一次吧 :)</li>
            <li>看看是否在邮箱的垃圾箱里?</li>
            <li>稍等几分钟，若仍旧没收到确认信，那就<a id="resend-email" class="text-muted" href="javascript:" data-url="${ctx}/register/email/send/${(user.id)!}/${hash!}">重发</a>一封吧！</li>
          </ul>
        </div>
        <p class="mtl">
          完成验证后，你还可以：
          <ul>
            <li><a href="${ctx}${_target_path}">返回原页面</a></li>
            <li><a href="${ctx}">浏览首页</a></li>
          </ul>
        </p>
      </div>

    </div><!-- /panel -->

  </div>

</div>
</#macro>