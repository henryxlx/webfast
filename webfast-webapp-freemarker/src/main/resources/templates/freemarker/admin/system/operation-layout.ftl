<#assign menu = 'operation'/>

<#include '/admin/system/layout.ftl'/>
<#macro blockTitle>全局设置 - ${blockTitleParent}</#macro>

<#macro blockMain>
<ul class="nav nav-tabs mbl">

    <#--<li class="<#if submenu! == 'course_setting'>active</#if>">
        <a href="${ctx}/admin/setting/course-setting">课程设置
        </a>
    </li>-->

    <#--<li class="<#if submenu! == 'questions_setting'>active</#if>">
        <a href="${ctx}/admin/setting/questions-setting">题库设置
        </a>
    </li>-->

    <li class="<#if submenu! == 'default'>active</#if>">
        <a href="${ctx}/admin/setting/default">系统默认设置
        </a>
    </li>

    <li class="<#if submenu! == 'mailer'>active</#if>">
        <a href="${ctx}/admin/setting/mailer">邮件服务器设置
        </a>
    </li>
    <li class="<#if submenu! == 'theme'>active</#if>">
        <a href="${ctx}/admin/setting/theme">主题设置</a>
    </li>

    <#--<li class="<#if submenu! == 'consult_setting'>active</#if>">
        <a href="${ctx}/admin/setting/consult-setting">客服设置
        </a>
    </li>-->

    <#--<li class="<#if submenu! == 'share'>active</#if>">
        <a href="${ctx}/admin/setting/share">分享设置
        </a>
    </li>-->

    <li class="<#if submenu! == 'customer_service'>active</#if>" style="display:none;">
        <a href="${ctx}/admin/customer/service">在线客户服务
        </a>
    </li>

</ul>

<#if blockMainContent??><@blockMainContent/></#if>
</#macro>
