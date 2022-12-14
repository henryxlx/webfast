<#assign modalSize = 'large'/>
<#include '/bootstrap-modal-layout.ftl'/>

<#macro blockTitle>添加新用户</#macro>

<#macro blockBody>

<form id="user-create-form" class="form-horizontal" method="post" action="${ctx}/admin/user/create">

    <div class="row form-group">
        <div class="col-md-2 control-label">
            <label for="email">邮箱地址</label>
        </div>
        <div class="col-md-7 controls">
            <input type="text" id="email" data-url="${ctx}/admin/user/create-email-check" name="email" class="form-control" >
        </div>
    </div>

    <div class="row form-group">
        <div class="col-md-2 control-label">
            <label for="nickname">用户名</label>
        </div>
        <div class="col-md-7 controls">
            <input type="text" id="username" name="username" data-url="${ctx}/admin/user/create-username-check" class="form-control" >
        </div>
    </div>

    <div class="row form-group">
        <div class="col-md-2 control-label">
            <label for="password">密码</label>
        </div>
        <div class="col-md-7 controls">
            <input type="password" id="password" name="password" class="form-control">
            <p class="help-block">5-20位英文、数字、符号，区分大小写</p>
        </div>
    </div>

    <div class="row form-group">
        <div class="col-md-2 control-label">
            <label for="confirmPassword">确认密码</label>
        </div>
        <div class="col-md-7 controls">
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control">
            <p class="help-block">再输入一次密码</p>
        </div>
    </div>

    <div class="row form-group">
        <div class="col-md-2 control-label">
            <label for="roles">用户权限</label>
        </div>
        <div class="col-md-7 controls">
            <input type="checkbox" value="ROLE_TEACHER" name="roles[]"> 教师
        </div>
    </div>

    <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">

</form>
</#macro>

<#macro blockFooter>
<button id="user-create-btn" data-submiting-text="正在提交" type="submit" class="btn btn-primary pull-right" data-toggle="form-submit" data-target="#user-create-form">提交</button>
<button type="button" class="btn btn-link pull-right" data-dismiss="modal">取消</button>
<script>app.load('user/create-modal')</script>
</#macro>