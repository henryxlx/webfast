<#assign menu = 'ip_blacklist'/>

<#include '/admin/system/layout.ftl'/>
<#macro blockTitle>IP黑名单 - ${blockTitleParent}</#macro>

<#macro blockMain>

<div class="page-header"><h1>IP黑名单</h1></div>

<@web_macro.flash_messages />

<form method="post">
	<textarea id="ips" name="ips" rows="18" class="form-control">${ips!}
</textarea>

  <div class="help-block">一行一个IP，被加入黑名单的IP将被禁止访问！暂不支持网段的封锁!</div>
  <input type="submit" class="btn btn-primary" value="提交">
  <input type="hidden" name="_csrf_token" value="${csrf_token('site')}">
</form>

</#macro>