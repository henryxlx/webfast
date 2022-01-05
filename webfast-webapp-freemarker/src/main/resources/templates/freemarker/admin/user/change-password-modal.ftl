<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>重置用户${(user.username)!}的密码</#macro>

<#macro blockBody>

  <form class="form-horizontal" id="change-password-form"  action="${ctx}/admin/user/${user.id}/change-password"  method="post">

      <div class="row form-group">
        <div class="col-md-3 control-label"><label for="code">用户昵称</label></div>
        <div class="col-md-8 controls"> 
          <div style="margin-top: 7px;">
          ${user.username}
          </div>
        </div>
      </div>

      <div class="row form-group">
        <div class="col-md-3 control-label"><label for="code">用户邮箱</label></div>
        <div class="col-md-8 controls"> 
          <div style="margin-top: 7px;">
          ${user.email}
          </div>
        </div>
      </div>

      <div class="row form-group">
        <div class="col-md-3 control-label"><label for="newPassword">新密码</label></div>
        <div class="col-md-8 controls"> 
        <input class="form-control" type="password" id="newPassword" value="" name="newPassword">
          <p class="help-block">5-20位英文、数字、符号，区分大小写</p>
        </div>
      </div>

       <div class="row form-group">
        <div class="col-md-3 control-label"><label for="confirmPassword">确认密码</label></div>
        <div class="col-md-8 controls"> 
          <input class="form-control" type="password" id="confirmPassword" value=""
          name="confirmPassword" data-explain="再输入一次密码">
          <p class="help-block">再输入一次密码</p>
        </div>
      </div>
      <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
  </form>

</#macro>

<#macro blockFooter>
  <button id="change-password-btn" data-submiting-text="正在提交" class="btn btn-primary pull-right" data-toggle="form-submit" data-target="#change-password-form">提交</button>
  <button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
 <script>app.load('user/change-password')</script>
</#macro>