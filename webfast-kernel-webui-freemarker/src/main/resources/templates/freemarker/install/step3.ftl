<#include '/install/layout.ftl'/>

<#macro blockMain>
<#if error??>
<div class="alert alert-danger">${error}</div>
</#if>

<form class="form-horizontal" id="init-form" method="post">

    <div class="form-group">
        <label for="sitename-field" class="col-sm-4 control-label">网站名称</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="sitename-field" name="sitename" value="${(site.sitename)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="email-field" class="col-sm-4 control-label">管理员Email地址</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="email-field" name="email" value="${(site.email)!''}">
            <p class="help-block">Email地址作为账号，用于登录网站</p>
        </div>
    </div>

    <div class="form-group">
        <label for="username-field" class="col-sm-4 control-label">管理员用户名</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="username-field" name="username" value="${(site.username)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="password-field" class="col-sm-4 control-label">管理员密码</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="password-field" name="password" value="${(site.password)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="truename-field" class="col-sm-4 control-label">网站负责人姓名</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="truename-field" name="truename" value="${(site.truename)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="mobile-field" class="col-sm-4 control-label">手机号码 </label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="mobile-field" name="mobile" value="${(site.mobile)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="qq-field" class="col-sm-4 control-label">QQ号码</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="qq-field" name="qq" value="${(site.qq)!''}">
        </div>
    </div>

    <div class="actions">
        <button type="submit" id="init-btn" class="btn btn-primary btn-lg">初始化系统</button>
    </div>

</form>
</#macro>

<#macro blockBottomScripts>
<script>
    seajs.use(['jquery', 'bootstrap.validator', 'common/validator-rules','bootstrap'], function($, Validator, Rules){
        window.$ = $;
        Rules.inject(Validator);
        var $form = $("#init-form");

        var validator = new Validator({
            element: $form,
            onFormValidated: function(error, results, $form){
                if (error) {
                    return false;
                }
                $('#init-btn').button('submiting').addClass('disabled');
            }
        });

        validator.addItem({
            element: '#sitename-field',
            required: true
        });

        validator.addItem({
            element: '#email-field',
            required: true,
            rule: 'email'
        });

        validator.addItem({
            element: '#nickname-field',
            required: true,
            rule: 'chinese_alphanumeric byte_minlength{min:4} byte_maxlength{max:14}'
        });

        validator.addItem({
            element: '#password-field',
            required: true,
            rule: 'minlength{min:5} maxlength{max:20}'
        });

        validator.addItem({
            element: '#truename-field',
            required: true,
            rule:'chinese minlength{min:2} maxlength{max:12}'
        });

        validator.addItem({
            element: '#mobile-field',
            required: true,
            rule: 'mobile'
        });

        validator.addItem({
            element: '#qq-field',
            required: true,
            rule: 'qq'
        });


    });
</script>

</#macro>