<ul class="nav navbar-nav">
    <#list navigations! as nav>
    <#if nav.isOpen gt 0>
        <#assign subNavs = nav.children![]/>
        <#assign subNavsNotEmpty = subNavs?size gt 0/>
    <li class="<#if subNavsNotEmpty> dropdown </#if><#if siteNav?? && siteNav == nav.url>active </#if>">
        <a href="${ctx}/<#if nav.url??>${nav.url!navigation_url!}<#else>javascript:;</#if>" class="<#if subNavsNotEmpty> dropdown-toggle </#if>" <#if nav.isNewWin gt 0>target="_blank"</#if> <#if subNavsNotEmpty> data-toggle="dropdown" </#if>>${nav.name} <#if subNavsNotEmpty> <b class="caret"></b></#if></a>
        <#if subNavsNotEmpty>
        <ul class="dropdown-menu">
            <#list subNavs! as subNav>
            <#if subNav.isOpen gt 0>
            <li><a href="${ctx}/${subNav.url!navigation_url!}"  <#if subNav.isNewWin gt 0>target="_blank"</#if>>${subNav.name}</a></li>
            </#if>
            </#list>
        </ul>
        </#if>
    </li>
    </#if>
    </#list>
</ul>