<#assign bodyClass = 'userpage'/>
<#assign script_controller = 'user/user'/>

<@block_title '${(user.username)!"佚名"}的公共主页'/>

<#include '/layout.ftl'/>

<#macro blockContent>

    <@renderController path='/user/headerBlock' params={'userId': user.id}/>

    <div class="es-row-wrap container-gap userpage-body">
        <ul class="nav nav-pills userpage-nav clearfix">
            <li <#if pageNav! == 'about'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/about">个人介绍</a></li>
        <li <#if pageNav! == 'friend'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/following">关注/粉丝</a></li>
        <li <#if pageNav! == 'group'>class="active"</#if>><a href="${ctx}/user/${(user.id)!}/group">加入的小组</a></li>
    </ul>

    <#if blockMain??><@blockMain/></#if>

</div>

</#macro>