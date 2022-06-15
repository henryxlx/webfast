<#include '/layout.ftl'>

<#macro blockTitle>重设密码成功 - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="row">
  <div class="col-md-6 col-md-offset-3 ptl">
    <div class="panel panel-default panel-page">
      <div class="panel-heading"><h2>重设密码成功</h2></div>
          <p class="text-success">新密码设置成功，请重新登录。</p>
          <p class=""><a href="${ctx}/login" class="btn btn-primary">登录</a></p>

    </div><!-- /panel -->

  </div>

</div>
</#macro>