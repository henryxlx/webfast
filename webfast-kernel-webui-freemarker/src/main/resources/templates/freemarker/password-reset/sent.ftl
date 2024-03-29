<@block_title '重设密码'/>

<#include '/layout.ftl'>

<#macro blockContent>
    <div class="row row-6">
        <div class="col-md-6 col-md-offset-3 ptl">
            <div class="panel panel-default panel-page">
                <div class="panel-heading"><h2>重设密码</h2></div>
                <p>
                    请到 <span class="text-success">${user.email}</span> 查阅来自${setting('site.name')}的邮件, 从邮件重设你的密码。
                </p>
                <p>
                    <a class="btn btn-primary" href="${emailLoginUrl}" target="_blank">登录邮箱查收邮件</a>
                </p>
            </div><!-- /panel -->
        </div>
</div>

</#macro>