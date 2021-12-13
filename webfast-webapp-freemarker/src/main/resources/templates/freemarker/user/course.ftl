<#include '/user/layout.ftl'/>

<#assign pageNav = type!/>

<#macro blockMain>
<div class="row">
    <div class="col-md-12">
        <#if courses??>
        <@renderController path='/course/coursesBlock' params={view: 'list'}/>
        <@web_macro.paginator paginator!/>
        <#else>
        <#if pageNav! == 'teach'>
        <div class="empty">无在教的课程</div>
        <#elseif pageNav! == 'learn'>
        <div class="empty">无在学的课程</div>
        <#elseif pageNav! == 'favorited'>
        <div class="empty">无收藏的课程</div>
        </#if>
        </#if>
    </div>
</div>
</#macro>

