<#assign script_controller = 'auth/password-reset-update'/>

<@block_title '重设密码'/>

<#include '/layout.ftl'>

<#macro blockContent>
  <div class="row">
    <div class="col-md-6 col-md-offset-3 ptl">
      <div class="panel panel-default panel-page">
        <div class="panel-heading"><h2>重设密码</h2></div>

        <form id="password-reset-update-form" class="form-vertical" method="post">

        <@web_macro.flash_messages/>

        <div class="form-group">
          <label class="control-label required" for="form_password">新密码</label>
          <div class="controls">
            <input type="password" id="form_password" name="password" required="required" class="form-control" />
          </div>
        </div>

        <div class="form-group">
          <label class="control-label required" for="form_confirmPassword">确认密码</label>
          <div class="controls">
            <input type="password" id="form_confirmPassword" name="confirmPassword" required="required" class="form-control" />
            <p class="help-block">请再输入一次密码</p>
          </div>
        </div>

        <div class="form-group">
          <div class="controls">
            <#-- {{ form_rest(form) }} -->
            <button type="submit" class="btn btn-primary">重设密码</button>
          </div>
        </div>

        <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

      </form>

    </div><!-- /panel -->

  </div>

</div>
</#macro>