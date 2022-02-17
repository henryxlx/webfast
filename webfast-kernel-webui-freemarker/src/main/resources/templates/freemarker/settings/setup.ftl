<#assign script_controller = 'settings/setup'/>

<#assign hideSetupHint = true />

<#include '/layout.ftl'>

<#macro blockTitle>设置帐号 - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="row row-6">
    <div class="col-md-6 col-md-offset-3">

        <div class="panel panel-default panel-page">
            <div class="panel-heading"><h2>设置帐号</h2></div>

            <#if !(appUser.setup)??>
            <form id="setup-form" class="form-vertical" method="post" action="${ctx}/settings/setup">

                <div class="alert alert-warning"> 为了帐号的安全以及更好的使用体验，请设置以下信息。 </div>

                <div class="form-group">
                    <label class="control-label" for="setup-email-field">Email地址</label>
                    <div class="controls">
                        <input class="form-control" id="setup-email-field" type="text" name="email" value="" data-url="${ctx}/register/email/check" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label" for="setup-nickname-field">昵称</label>
                    <div class="controls">
                        <input class="form-control" id="setup-nickname-field" type="text" name="nickname" value="${(appUser.nickname)!}" data-url="settings/setup/checkNickname" />
                        <div class="help-block">昵称一旦保存，就不能再修改了</div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="controls">
                        <button type="submit" class="btn btn-fat btn-primary btn-large" data-loading-text="正在保存..." data-goto="${ctx}/">保存</button>
                    </div>
                </div>

                <input type="hidden" name="_csrf_token" value="{{ csrf_token('site') }}">
            </form>
            <#else>
            <div class="alert alert-danger">
                <p>该帐号已经设置过Email地址和昵称，不能再次设置！</p>
                <p><a href="${ctx}/" class="btn btn-primary">返回首页</a></p>
            </div>
            </#if>

        </div>

    </div>
</div>
</#macro>