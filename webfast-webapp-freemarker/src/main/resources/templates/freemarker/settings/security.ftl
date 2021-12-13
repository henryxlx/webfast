<#assign side_nav = 'security'/>
<#assign script_controller = 'settings/security'/>

<#include '/settings/layout.ftl'/>

<#macro blockTitle>安全设置 - ${blockTitleParent}</#macro>

<#macro blockMain>
    <#local class='panel-col'/>
    <#include '/bootstrap/panel.ftl'/>
</#macro>

<#macro blockHeading>安全设置</#macro>
<#macro blockPanelBody>
<@web_macro.flash_messages />

<div class="row safety-setting">
    <div class="col-md-2 tar">
        <span class="esicon esicon-safety <#if progressScore!0 gt 90>text-success<#elseif progressScore!0 gt 60>text-warning<#else>text-danger</#if>" style="font-size:32px"></span>
    </div>
    <div class="col-md-6">
        <div class="progress" style="margin-top:10px;">
            <div class="progress-bar" role="progressbar" style="width: ${progressScore!}%;"></div>
        </div>
    </div>
    <div class="col-md-4">
        <#if progressScore!0 gt 80>
        <span class="text-success" style="display:block;margin-top:10px;">安全等级：高</span>
        <#elseif progressScore!0 gt 30>
        <span class="text-warning" style="display:block;margin-top:10px;">安全等级：中</span>
        <#else>
        <span class="text-danger" style="display:block;margin-top:10px;">安全等级：低</span>
        </#if>
    </div>
</div>

<hr>

<div class="row">
    <div class="col-md-2 tar
				<#if hasLoginPassword!false == true>
					text-success
				<#else>
					text-danger
				</#if>
			" style="font-size:20px">
        <#if hasLoginPassword!false == true>
        <span class="glyphicon glyphicon-ok"></span>
        <#else>
        <span class="glyphicon glyphicon-warning-sign"></span>
        </#if>
    </div>

    <span class="col-md-3 " style="margin-top: 5px;" >登录密码</span>

    <span class="col-md-4 " style="margin-top: 5px;" >登录网校时需要输入的密码</span>

    <a  href="${ctx}/settings/password" class="col-md-offset-1  btn btn-primary" style="margin-top: -3px;" >修改</a>
</div>
<hr>

<div class="row">
    <div class="col-md-2 tar
				<#if hasPayPassword!false == true>
					text-success
				<#else>
					text-danger
				</#if>
			" style="font-size:20px">
        <#if hasPayPassword!false == true>
        <span class="glyphicon glyphicon-ok"></span>
        <#else>
        <span class="glyphicon glyphicon-warning-sign"></span>
        </#if>
    </div>

    <span class="col-md-3 " style="margin-top: 5px;" >支付密码</span>

    <span class="col-md-4 " style="margin-top: 5px;" >在网校进行消费行为时需要输入的密码</span>

    <#if hasPayPassword!false == true>
    <a href="{{ path('settings_reset_pay_password') }}" class="col-md-offset-1 btn btn-primary" style="margin-top: -3px;" >重置</a>
    <#else>
    <a href="{{ path('settings_pay_password') }}" class="col-md-offset-1 btn btn-primary" style="margin-top: -3px;" >设置</a>
    </#if>

</div>
<hr>

<div class="row">
    <div class="col-md-2 tar
				<#if hasFindPayPasswordQuestion!false == true>
					text-success
				<#else>
					text-danger
				</#if>	"
         style="font-size:20px">

        <#if hasFindPayPasswordQuestion!false == true>
        <span class="glyphicon glyphicon-ok"></span>
        <#else>
        <span class="glyphicon glyphicon-warning-sign"></span>
        </#if>
    </div>

    <span class="col-md-3 " style="margin-top: 5px;" >安全问题</span>

    <span class="col-md-4 " style="margin-top: 5px;" >通过设置并且验证安全问题，保护帐号密码安全。</span>

    <a href="{{ path('settings_security_questions') }}" class="col-md-offset-1 btn btn-primary" style="margin-top: -3px;" >
        <#if hasFindPayPasswordQuestion!false == true>
        查看
        <#else>
        设置
        </#if>

    </a>
</div>

<#if setting('cloud_sms.sms_enabled')! == '1' && setting('cloud_sms.sms_bind')!'' == 'on'>
<hr>

<div class="row">
    <div class="col-md-2 tar
					<#if hasVerifiedMobile!false == true>
						text-success
					<#else>
						text-danger
					</#if>	"
         style="font-size:20px">

        <#if hasVerifiedMobile!false == true>
        <span class="glyphicon glyphicon-ok"></span>
        <#else>
        <span class="glyphicon glyphicon-warning-sign"></span>
        </#if>
    </div>

    <span class="col-md-3 " style="margin-top: 5px;" >手机绑定</span>

    <span class="col-md-4 " style="margin-top: 5px;" >增加账户登陆、购买课程时的安全性，同时还可以找回登陆密码，支付密码。</span>

    <a href="${ctx}/settings/bindMobile" class="col-md-offset-1 btn btn-primary" style="margin-top: -3px;" >
        <#if hasVerifiedMobile!false == true>
        修改
        <#else>
        绑定
        </#if>

    </a>
</div>
</#if>

</#macro>