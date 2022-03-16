<#assign pageNav = 'about'/>

<#include '/user/layout.ftl'/>

<#macro blockMain>
<div class="editor-text">

    <#if (userProfile.aboutme)??>
        <div class="empty">${userProfile.aboutme}</div>
    <#else>
        <div class="empty">暂无个人介绍</div>
    </#if>
</div>
</#macro>