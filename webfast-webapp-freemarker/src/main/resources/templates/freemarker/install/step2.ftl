<#include '/install/layout.ftl'/>

<#macro blockMain>
<#if error??>
<div class="alert alert-danger">${error}</div>
</#if>

<form class="form-horizontal" id="create-database-form" method="post">

    <div class="form-group">
        <label for="database-host-field" class="col-sm-4 control-label">数据库服务器</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="database-host-field" name="host" value="${(post.host)!'localhost'}">
            <div class="help-block">数据库服务器地址，一般为localhost</div>
        </div>
    </div>

    <div class="form-group">
        <label for="database-port-field" class="col-sm-4 control-label">数据库端口号</label>
        <div class="controls col-sm-5">
            <input type="text" class="form-control" id="database-port-field" name="port" value="${(post.port)!'3306'}">
            <div class="help-block">数据库端口号，默认为3306</div>
        </div>
    </div>

    <div class="form-group">
        <label for="database-user-field" class="col-sm-4 control-label">数据库用户名</label>
        <div class="controls col-sm-5">
            <input type="text" id="database-user-field" class="form-control" name="user" value="${(post.user)!'root'}">
        </div>
    </div>

    <div class="form-group">
        <label for="database-password-field" class="col-sm-4 control-label">数据库密码</label>
        <div class="controls col-sm-5">
            <input type="password" class="form-control" id="database-password-field" name="password"  value="${(post.password)!''}">
        </div>
    </div>

    <div class="form-group">
        <label for="database-name-field" class="col-sm-4 control-label">数据库名</label>
        <div class="controls col-sm-5">
            <input type="text" id="database-name-field" class="form-control" name="dbname" value="${(post.dbname)!'webfast'}">
        </div>
    </div>

    <div class="form-group">
        <div class="controls col-sm-offset-4 col-sm-5">
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="replaceDatabase" value="1" <#if (post.replaceDatabase)?? && post.replaceDatabase>checked</#if>> 覆盖现有数据库
                </label>
            </div>
        </div>
    </div>

    <div class="actions">
        <button type="submit" id="create-database-btn" class="btn btn-primary btn-lg">创建数据库</button>
    </div>

</form>

</#macro>

<#macro blockBottomScripts>
<script>
    seajs.use(['jquery', 'bootstrap.validator'], function($, Validator){
        window.$ = $;
        var $form = $("#create-database-form");

        var validator = new Validator({
            element: $form,
            autoSubmit: false,
            onFormValidated: function(error, results, $form) {
                if (error) {
                    return false;
                }

                var checked = $form.find('[name=replaceData]').is(':checked');

                if (checked) {
                    if (!confirm('覆盖数据库，会删除原有的数据。您真的要覆盖当前数据库吗？')) {
                        return false;
                    }
                }

                $form[0].submit();

            }
        });

        validator.addItem({
            element: '#database-host-field',
            required: true
        });

        validator.addItem({
            element: '#database-user-field',
            required: true
        });

        validator.addItem({
            element: '#database-name-field',
            required: true
        });

    });
</script>

</#macro>