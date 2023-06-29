<#assign menu = 'site'/>

<@block_title '站点设置'/>

<#include '/admin/system/layout.ftl'/>

<#macro blockMain>
    <ul class="nav nav-tabs mbl">
        <li class="<#if submenu! == 'base'>active</#if>">
            <a href="${ctx}/admin/setting/site">网站基本信息
            </a>
        </li>
        <li class="<#if submenu! == 'offline'>active</#if>">
            <a href="${ctx}/admin/setting/site/offline">离线设置
            </a>
        </li>
    </ul>


    <#if blockMainContent??><@blockMainContent/></#if>
</#macro>