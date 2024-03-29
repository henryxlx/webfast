<@block_title '密码重设失败'/>

<#include '/layout.ftl'>

<#macro blockContent>
  <div class="row">
    <div class="col-md-6 col-md-offset-3 ptl">
      <div class="panel panel-default panel-page">
        <div class="panel-heading"><h2>密码重设失败</h2></div>

        <p class="alert alert-danger">密码重设地址无效或已过期，请重试。</p>

        <p><a href="${ctx}/password/reset" class="btn btn-primary">重设密码</a></p>

      </div><!-- /panel -->

    </div>

  </div>
</#macro>