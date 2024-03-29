<#assign menu = 'user_set'/>

<@block_title '用户相关设置'/>

<#include '/admin/system/layout.ftl'/>

<#macro blockMain>
    <ul class="nav nav-tabs mbl">
        <li class="<#if submenu! == 'auth'>active</#if>">
            <a href="${ctx}/admin/setting/auth">注册设置
            </a>
        </li>
        <li class="<#if submenu! == 'login_bind'>active</#if>">
        <a href="${ctx}/admin/setting/login-connect">登录设置
        </a>
    </li>
    <li class="<#if submenu! == 'user_center'>active</#if>">
        <a href="${ctx}/admin/setting/user-center">用户中心设置
        </a>
    </li>
    <li class="<#if submenu! == 'user_fields'>active</#if>">
        <a href="${ctx}/admin/setting/user-fields">字段自定义
        </a>
    </li>
    <li class="<#if submenu! == 'user_avatar'>active</#if>">
        <a href="${ctx}/admin/setting/user-avatar">用户默认头像
        </a>
    </li>
</ul>


<#if blockMainContent??><@blockMainContent/></#if>
</#macro>