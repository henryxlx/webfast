<#assign nav = 'system'/>

<#include '/admin/layout.ftl'/>

<#macro blockContent>
<div class="col-md-2">
    <#if blockSidebar??><@blockSidebar/><#else>
    <div class="list-group">
        <a href="${ctx}/admin/setting/site" class="list-group-item <#if menu! == 'site'>active</#if>">站点设置</a>
        <a href="${ctx}/admin/setting/default" class="list-group-item <#if menu! == 'operation'>active</#if>">全局设置</a>

<#--        <a href="${ctx}/admin/setting/cloud" class="list-group-item <#if menu! == 'cloud'>active</#if>">云平台设置</a>-->

        <a href="${ctx}/admin/setting/auth" class="list-group-item <#if menu! == 'user_set'>active</#if>">用户相关设置</a>
        <a href="${ctx}/admin/setting/mobile" class="list-group-item <#if menu! == 'client'>active</#if>">移动客户端设置</a>
        <a href="${ctx}/admin/setting/ip-blacklist" class="list-group-item <#if menu! == 'ip_blacklist'>active</#if>">IP黑名单</a>

        <a href="${ctx}/admin/optimize" class="list-group-item <#if menu! == 'optimize'>active</#if>">优化和备份</a>

<#--        <a href="${ctx}/admin/operation/analysis/register/trend?analysisDateType=register" class="list-group-item <#if menu! == 'analysis'>active</#if>">数据统计</a>-->
        <a href="${ctx}/admin/logs" class="list-group-item <#if menu! == 'logs'>active</#if>">系统日志</a>

    </div>
    </#if>
</div>
<div class="col-md-10">
    <#if blockMain??><@blockMain/></#if>
</div>
</#macro>