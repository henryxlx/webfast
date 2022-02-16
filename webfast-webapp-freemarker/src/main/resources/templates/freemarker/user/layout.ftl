<#assign bodyClass = 'userpage'/>
<#assign script_controller = 'user/user'/>

<#include '/layout.ftl'/>

<#macro blockTitle>${(user.nickname)!'佚名'}的公共主页 - ${blockTitleParent}</#macro>

<#macro blockContent>

<@renderController path='/user/headerBlock'/>

<div class="es-row-wrap container-gap userpage-body">
    <ul class="nav nav-pills userpage-nav clearfix">
        <li <#if pageNav! == 'about'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/about">个人介绍</a></li>
        <li <#if pageNav! == 'favorited'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/favorited">收藏的内容</a></li>
        <li <#if pageNav! == 'group'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/group">加入的小组</a></li>
        <li <#if pageNav! == 'following'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/following">关注/粉丝</a></li>

        <#if setting('classroom.enabled')??>
        <#if (user.roles)?? && user.roles?contains('ROLE_TEACHER')>
        <li <#if pageNav! == 'teaching'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/teaching/classrooms">在教${setting('classroom.name', '班级')}</a></li>
        </#if>

        <li <#if pageNav! == 'learning'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/learning/classrooms">在学${setting('classroom.name', '班级')}</a></li>
        </#if>
    </ul>

    <#if blockMain??><@blockMain/></#if>

</div>

</#macro>