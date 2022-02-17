<#include '/layout.ftl'>

<#macro blockTitle>个人中心 - ${blockTitleParent}</#macro>

<#macro blockContent>
<div class="row">
    <div class="col-md-3">
        <#if blockSidebar??><@blockSidebar/><#else>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="list-group-block">
                    <div class="list-group-panel">

                        <div class="list-group-heading">个人中心</div>
                        <ul class="list-group">

                            <a class="list-group-item <#if side_nav! == 'profile'>active</#if>" href="${ctx}/settings"><i class="glyphicon glyphicon-user"></i> 基础信息</a>

                            <a class="list-group-item <#if side_nav! == 'avatar'>active</#if>" href="${ctx}/settings/avatar"><i class="glyphicon glyphicon-picture"></i> 头像设置</a>

                            <a class="list-group-item <#if side_nav! == 'security'>active</#if>" href="${ctx}/settings/security"><i class="glyphicon glyphicon-lock"></i> 安全设置</a>

<#--                            <a class="list-group-item <#if side_nav! == 'password'>active</#if>" href="${ctx}/settings/password"><i class="glyphicon glyphicon-lock"></i> 登陆密码修改</a>-->

                            <a class="list-group-item <#if side_nav! == 'email'>active</#if>" href="${ctx}/settings/email"><i class="glyphicon glyphicon-envelope"></i> 邮箱设置</a>

                            <#if setting('login_bind.enabled')??>
                            <a class="list-group-item <#if side_nav! == 'binds'>active</#if>" href="${ctx}/settings/binds"><i class="glyphicon glyphicon-circle-arrow-right"></i> 第三方登录</a>
                            </#if>

                        </ul>
                    </div>

                </div><!-- /list-group-block -->
            </div>

        </div>
        </#if>
    </div>
    <div class="col-md-9">
        <#if blockMain??><@blockMain/></#if>
    </div>
</div>
</#macro>