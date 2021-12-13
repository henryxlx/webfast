<#assign script_controller = 'auth/login'/>
<#include '/layout.ftl'>

<#macro blockTitle>登录 - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="row row-6">
    <div class="col-md-6 col-md-offset-3">

        <div class="panel panel-default panel-page">
            <div class="panel-heading"><h2>登录</h2></div>

            <form id="login-form" class="form-vertical" method="post" action="${ctx}/login">

                <#if errorMessage??>
                <div class="alert alert-danger">${errorMessage}</div>
                </#if>
                <@web_macro.flash_messages/>
                <div class="form-group">
                    <label class="control-label" for="login_username">帐号</label>
                    <div class="controls">
                        <input class="form-control" id="login_username" type="text" name="_username" value="${last_username!'admin'}" required />
                        <div class="help-block">请输入Email地址 / 用户昵称</div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label" for="login_password">密码</label>
                    <div class="controls">
                        <input class="form-control" id="login_password" type="password" name="_password" value="admin123" required />
                    </div>
                </div>

                <div class="form-group">
                    <div class="controls">
            <span class="checkbox mtm pull-right">
              <label> <input type="checkbox" name="_remember_me" checked="checked"> 记住密码 </label>
            </span>
                        <button type="submit" class="btn btn-fat btn-primary btn-large">登录</button>
                    </div>
                </div>

                <input type="hidden" name="_target_path" value="${_target_path!}">
                <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
            </form>

            <div class="ptl">
                <a href="${ctx}/password/reset">找回密码</a>
                <span class="text-muted mhs">|</span>
                <span class="text-muted">还没有注册帐号？</span>
                <a href="${ctx}/register?goto:${_target_path!}">立即注册</a>
            </div>

            <#if setting('login_bind.enabled')??>
            <div class="social-logins">
                <@renderController path='/login/oauth2LoginsBlock?targetPath=' + targetPath!/>
            </div>
            </#if>
        </div>

    </div>
</div>
</#macro>