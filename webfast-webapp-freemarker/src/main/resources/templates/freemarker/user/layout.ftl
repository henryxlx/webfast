<#assign bodyClass = 'userpage'/>
<#assign script_controller = 'user/user'/>

<#include '/layout.ftl'/>

<#macro blockTitle>${(user.nickname)!'佚名'}的公共主页 - ${blockTitleParent}</#macro>

<#macro blockContent>

<@renderController path='/user/headerBlock'/>

<div class="es-row-wrap container-gap userpage-body">
    <ul class="nav nav-pills userpage-nav clearfix">
        <#if (user.roles)?? && user.roles?seq_contains('ROLE_TEACHER')>
        <li <#if pageNav! == 'teach'>class="active"</#if>><a href="${ctx}/user/teach?id=${(user.id)!}">在教课程</a></li>
        </#if>
        <li <#if pageNav! == 'learn'>class="active"</#if>><a href="${ctx}/user/learn?id=${(user.id)!}">在学课程</a></li>
        <li <#if pageNav! == 'favorited'>class="active"</#if>><a href="${ctx}/user/favorited?id=${(user.id)!}">收藏的课程</a></li>
        <li <#if pageNav! == 'group'>class="active"</#if>><a href="${ctx}/user/group?id=${(user.id)!}">加入的小组</a></li>
        <li <#if pageNav! == 'friend'>class="active"</#if>><a href="${ctx}/user/following?id=${(user.id)!}">关注/粉丝</a></li>

        <#if setting('classroom.enabled')??>
        <#if (user.roles)?? && user.roles?seq_contains('ROLE_TEACHER')>
        <li <#if pageNav! == 'teaching'>class="active"</#if>><a href="${ctx}/user/teaching/classrooms?id=${(user.id)!}">在教${setting('classroom.name', '班级')}</a></li>
        </#if>

        <li <#if pageNav! == 'learning'>class="active"</#if>><a href="${ctx}/user/learning/classrooms?id=${(user.id)!}">在学${setting('classroom.name', '班级')}</a></li>
        </#if>
    </ul>

    <#if blockMain??><@blockMain/></#if>

</div>

</#macro>